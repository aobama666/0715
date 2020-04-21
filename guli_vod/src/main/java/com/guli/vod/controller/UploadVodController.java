package com.guli.vod.controller;

import com.guli.common.entity.Result;
import com.guli.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("vod")
public class UploadVodController {

    @Autowired
    private VodService vodService;

    @PostMapping("upload")
    public Result upload(MultipartFile file){
        String videoSourceId = vodService.uploadVod(file);
        return Result.ok().data("videoSourceId", videoSourceId);
    }

    @DeleteMapping("removeById/{videoSourceId}")
    public Result removeById(@PathVariable("videoSourceId") String videoSourceId){
        vodService.removeById(videoSourceId);
        return Result.ok();
    }

    /**
     * 批量删除的
     * @param ids
     * @return
     */
    @DeleteMapping("removeByIds")
    public Result removeById(@RequestParam("ids") List<String> ids){
        vodService.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 根据视频Id获取播放凭证
     */
    @GetMapping("getPlayAuth/{videoSourceId}")
    public Result getPlayAuth(@PathVariable("videoSourceId") String videoSourceId){
        String playAuth = vodService.getPlayAuthById(videoSourceId);
        return Result.ok().data("playAuth", playAuth);
    }


}
