package com.guli.ucenter.controller;

import com.google.gson.Gson;
import com.guli.common.entity.Result;
import com.guli.common.entity.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.ucenter.entity.Member;
import com.guli.ucenter.service.MemberService;
import com.guli.ucenter.utils.ConstantPropertiesUtil;
import com.guli.ucenter.utils.HttpClientUtils;
import com.guli.ucenter.utils.JwtUtils;
import com.sun.deploy.net.URLEncoder;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WeixinController {


    @Autowired
    private MemberService memberService;

    @GetMapping("login")
    public String genQrConnect(HttpSession session) {

        System.out.println("sessionId = " + session.getId());

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20021,"URL编码失败");
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "huaan";//为了让大家能够使用微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("生成 state = " + state);
        session.setAttribute("wx-open-state", state);

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);

        return "redirect:" + qrcodeUrl;
    }

    /**
     * 微信授权成功回调方法
     * @PathVariable ： 是通过URL的占位符中获取参数的；此URL是RESTFUL风格的
     *                   user/getById/{id}  //{}占位符
     * @RequestParam ： 获取URL后面？的参数的
     *                  user/getById/{id}?state=2
     * @RequestBody  :  是用来接收请求过来的数据对象的
     *                   前端form表单传递的JSON数据，会帮我们转成后台接口的对象
     *                   前端AJAX（异步）传递过来的JSON数据，帮我们转成后台接收的对象
     * @ResponseBody ： 响应用的，把对象响应给前端并转成JSON数据；
     *                    后台给前端响应对象的时候，需要转成JSON数据给前端
     */
//    根据ID删除用户  http://localhost:8004/user/1  请求方式：Delete
//    根据ID获取用户  http://localhost:8004/user/1?***  请求方式：Get
    @GetMapping("callback")
    public String getCallback(String code, String state, HttpSession session){
        // 判断state是否合法
//        String stateStr = (String)session.getAttribute("wx-open-state");
//        System.out.println("存储 state = " + stateStr);
//        //2、可以做state的校验
//        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(stateStr) || !state.equals(stateStr)) {
//            throw new GuliException(20022,"非法数据");
//        }

        //3、根据这个code来获取ACCESS_TOKEN和OPENID
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        //3.1 给URL赋值
        String qrTokenUrl = String.format(
                url,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);
        String result = null;
        try {
            result = HttpClientUtils.get(qrTokenUrl);
            System.out.println(result);
        } catch (Exception e) {
            throw new GuliException(20023,"根据code获取ACCESS_TOKEN失败");
        }
        Gson gson = new Gson();
        //把json字符串转成Map对象
        Map<String, Object> resultMap = gson.fromJson(result, HashMap.class);
        if (resultMap.get("errcode") != null) {
            throw new GuliException(20024,"JSON数据转Map对象失败");
        }
        //从Map对象中取ACCESS_TOKEN和OpenId
        String accessToken = (String) resultMap.get("access_token");
        String openid = (String) resultMap.get("openid");
        //openId：是微信用户对外开放的唯一标示；
        //4、根据OPENID判断数据库中是否存在这个用户
        Member member = memberService.getMemberByOpenId(openid);
        //5、如果存在获取用户信息，当重定向到前端页面的时候带着用户信息
        if(member == null){
            //6、如果不存在：根据OPENID和ACCESS_TOKEN获取用户信息
            String wxUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userUrl = String.format(
                    wxUrl,
                    accessToken,
                    openid
            );
            String resultParam = null;
            try {
                resultParam = HttpClientUtils.get(userUrl);
                System.out.println(result);
            } catch (Exception e) {
                throw new GuliException(20024,"获取微信用户信息失败");
            }
            Map<String, Object> resultUserMap = gson.fromJson(resultParam, HashMap.class);
            if (resultUserMap.get("errcode") != null) {
                throw new GuliException(20024,"JSON数据转Map对象失败");
            }
            String openId = (String)resultUserMap.get("openid");
            String nickname = (String)resultUserMap.get("nickname");
            String headimgurl = (String)resultUserMap.get("headimgurl");
            //7、添加到数据库
            member = new Member();
            member.setOpenid(openId);
            member.setNickname(nickname);
            member.setAvatar(headimgurl);
            member.setGmtCreate(new Date());
            memberService.save(member);
        }

        //把用户信息传递给前端思路？
        //1、使用一个工具把用户对象转码加密成token密钥
            //把用户信息根据一个规则来加密
        //2、把密钥传递过去
        String token = JwtUtils.generateJWT(member);
        //重定向到前端工程
        return "redirect:http://localhost:3000?token="+token;

    }

    /**
     * 根据前端传递过来的token获取用户信息
     * //根据一个规则来解析
     * 当页面加载的时候，获取URL中的token参数，然后根据token调用后台接口获取用户信息
     */
    @GetMapping("getMemberByToken/{token}")
    @ResponseBody //把对象转成JSON数据响应
    public Result getMemberByToken(@PathVariable("token") String token){
        Claims claims = JwtUtils.checkJWT(token);
        String id = (String)claims.get("id");
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");
        Member member = new Member();
        member.setId(id);
        member.setNickname(nickname);
        member.setAvatar(avatar);
        return Result.ok().data("member",member);
    }


}
