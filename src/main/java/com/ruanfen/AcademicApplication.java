package com.ruanfen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ruanfen.mapper")
public class AcademicApplication
{
    public static void main(String[] args){
        SpringApplication.run(AcademicApplication.class, args);
    }

}
