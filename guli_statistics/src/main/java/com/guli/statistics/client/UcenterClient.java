package com.guli.statistics.client;

import com.guli.common.entity.Result;
import com.guli.statistics.client.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value="guli-ucenter", fallback = UcenterClientImpl.class) //指定的服务的名称
public interface UcenterClient {

    /**
     * 注意：一定要写成 @PathVariable("day")，圆括号中的"day"不能少
     * @param day
     * @return
     */
    @GetMapping(value = "/ucenter/member/registerCount/{day}")
    public Result registerCount(@PathVariable("day") String day);
}
