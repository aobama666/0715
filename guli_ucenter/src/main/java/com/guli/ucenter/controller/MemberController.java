package com.guli.ucenter.controller;


import com.guli.common.entity.Result;
import com.guli.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author huaan
 * @since 2019-12-11
 */
@RestController
@CrossOrigin
@RequestMapping("/ucenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 根据时间获取注册人数的个数
     */
    @GetMapping("registerCount/{day}")
    public Result getRegisterCountByDay(@PathVariable("day") String day){
        Integer count = memberService.getRegisterCountByDay(day);
        return Result.ok().data("count", count);
    }


}

