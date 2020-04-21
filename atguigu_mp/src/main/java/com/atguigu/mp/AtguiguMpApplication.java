package com.atguigu.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**
 * 规则：在自动类启动的时候，
 * 默认加载此包及其子包下的所有的注解类
 * 分类型加载
 */
@SpringBootApplication
public class AtguiguMpApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtguiguMpApplication.class, args);
	}

}
