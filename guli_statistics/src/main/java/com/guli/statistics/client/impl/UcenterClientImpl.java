package com.guli.statistics.client.impl;

import com.guli.common.entity.Result;
import com.guli.common.exception.GuliException;
import com.guli.statistics.client.UcenterClient;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient{
    @Override
    public Result registerCount(String day) {

        System.err.println("进行没有调用服务的逻辑处理");
        System.err.println("调用发短信和发邮件的消息给运维人员开发人员");

        return Result.error();
    }
}
