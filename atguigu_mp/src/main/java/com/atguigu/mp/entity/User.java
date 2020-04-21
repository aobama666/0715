package com.atguigu.mp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    //MP自动生成主键默认是 ID_WORKER ： 生成64位的ID，这个Id是根据规则来生成的，默认是Long类型
    // 在企业中很多Id是String类型的
    //那么一般使用 ID_WORKER_STR
    @TableId(type = IdType.ID_WORKER)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    @TableLogic //逻辑删除的字段的标示
    private Integer deleted; // 删除状态的

    @Version
    private Integer version;

    //需要创建一个类来管理此属性，让他们自动补全
    //设置此属性被域管理：被怎么管理拦截：只是在insert语句的时候才被管理
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //在执行SQL语句的时候：insert和update语句此属性被管理拦截
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;



}
