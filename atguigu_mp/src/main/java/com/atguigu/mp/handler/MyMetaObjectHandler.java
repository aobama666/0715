package com.atguigu.mp.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    // insertFill : 在插入insert语句的时候，被拦截执行
    @Override
    public void insertFill(MetaObject metaObject) {
        // 自动补全
        //参数说明：1、是指定的属性的名称；2、赋值给属性的数据；3、拦截的原数据
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    //updateFill 在执行update语句的时候被拦截执行
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
