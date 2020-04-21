package com.guli.edu.controller;


import com.guli.common.entity.Result;
import com.guli.edu.entity.Video;
import com.guli.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author huaan
 * @since 2019-12-04
 */
@RestController
@CrossOrigin
@RequestMapping("/edu/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 保存Video
     */
    @PostMapping("save")
    public Result save(@RequestBody Video video){
        videoService.save(video);
        return Result.ok();
    }

    /**
     * 根据VIdeoId获取课时的信息
     */
    @GetMapping("getVideoById/{videoId}")
    public Result getVideoById(@PathVariable("videoId") String videoId){
        Video video = videoService.getById(videoId);
        return Result.ok().data("video", video);
    }

    /**
     * 修改课时
     */
    @PutMapping("update")
    public Result udpateById(@RequestBody Video video){
        videoService.updateById(video);
        return Result.ok();
    }

    /**
     * 根据课时Id删除课时信息
     */
    @DeleteMapping("removeById/{videoId}")
    public Result removeById(@PathVariable("videoId") String videoId){
        videoService.removeVideoById(videoId);
        return  Result.ok();
    }

}

