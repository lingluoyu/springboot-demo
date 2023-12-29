package com.lingluoyu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.lingluoyu.mapper")
public class MybatisConfig {
}
