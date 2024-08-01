package com.learning.fmsmybatisliquibase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.learning.fmsmybatisliquibase.dao")
public class FmsMybatisLiquibaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmsMybatisLiquibaseApplication.class, args);
    }

}
