package com.guli.edu.client;

import com.guli.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("guli-vod")
public interface VodClient {

    @DeleteMapping("vod/removeById/{videoSourceId}")
    public Result removeById(@PathVariable("videoSourceId") String videoSourceId);

}
