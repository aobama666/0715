package com.guli.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过
 滤器类型，具体如下：
 pre ：可以在请求被路由之前调用
 route ：在路由请求时候被调用
 post ：在route和error过滤器之后被调用
 error ：处理请求时发生错误时被调用
 filterOrder ：通过int值来定义过滤器的执行顺序
 shouldFilter ：返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可
 实现过滤器的开关。在上例中，我们直接返回true，所以该过滤器总是生效
 run ：过滤器的具体逻辑。

 */
@Component
public class WebZuulFilter extends ZuulFilter {
    /**
     * 前置过滤器
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0; // 优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true; // 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public Object run() throws ZuulException {
        //每一次请求都会进入这个方法，
        //在此方法中进行校验
        System.err.println("已拦截...");
        // 获取内容并且校验，把他它放进request

        RequestContext requestContext=RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }
        String url=request.getRequestURL().toString();
        if(url.indexOf("/admin/login")>0){
            System.out.println("登陆页面"+url);
            //放行
            return null;
        }
        // JWT : token  Bearer 64位
        String authHeader =(String)request.getHeader("guli-token");//获取头信息
        if(authHeader!=null && authHeader.startsWith("guli-") ){
            String token = authHeader.substring(5);
            System.err.println("用户合法：" + token);
            // 根据token获取用户信息
            /*Claims claims = jwtUtil.parseJWT(token);
            if(claims!=null){
                claims.get("id");
                claims.get("nickname");
                if("admin".equals(claims.get("roles"))){
                    requestContext.addZuulRequestHeader("Authorization",authHeader);
                    System.out.println("token 验证通过，添加了头信
                            息"+authHeader);

                }
            }*/
            return null;
        }
        requestContext.setSendZuulResponse(false);//终止运行
        requestContext.setResponseStatusCode(401);//http状态码
        requestContext.setResponseBody("wu quan fang wen");
        requestContext.getResponse().setContentType("text/html;charset=UTF‐8");

        return null;

    }
}
