package com.atguigu.mp.mapper;

import com.atguigu.mp.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface UserMapper extends BaseMapper<User>{
}
