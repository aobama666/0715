package com.guli.statistics.controller;


import com.guli.common.entity.Result;
import com.guli.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author huaan
 * @since 2019-12-11
 */
@RestController
@CrossOrigin
@RequestMapping("/statistics/daily")
public class DailyController {

    @Autowired
    private DailyService dailyService;
    /**
     * 生成统计
     */
    @GetMapping("createDaily/{day}")
    public Result createDailyByDay(@PathVariable("day") String day){
        dailyService.createDailyByDay(day);
        return Result.ok();
    }

    /**
     * 根据查询的类型和时间查询注册人数个数和时间的集合
     */
    @GetMapping("show/{type}/{begin}/{end}")
    public Result show(
            @PathVariable("type") String type,
            @PathVariable("begin") String begin,
            @PathVariable("end") String end){
        Map<String, Object> map = dailyService.getShow(type, begin, end);
        return Result.ok().data(map);
    }


}

