package com.guli.oss.controller;

import com.guli.common.entity.Result;
import com.guli.oss.service.OssService;
import com.guli.oss.util.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("oss")
@CrossOrigin
public class OSSController {

    @Autowired
    private OssService ossService;
    /**
     * 在Swagger中测试文件上传——>上传到OSS中
     * 如果在页面中上传完了、回显图片；
     */
    @PostMapping("file/upload")
    public Result upload(MultipartFile file,
                         @RequestParam(required = false) String host){ //cover
        if(host != null){
            ConstantPropertiesUtil.FILE_HOST = host;
        }
        String url = ossService.upload(file);
        if(url == null){
            return Result.error();
        }
        return Result.ok().data("url", url);
    }

}
