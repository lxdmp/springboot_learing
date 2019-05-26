package com.lxdmp.springboottest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
/*
@ComponentScan(basePackages = {
	"com.lxdmp.springboottest.controller"
})
*/
@MapperScan("com.lxdmp.springboottest.mapper")
@EnableScheduling
public class Application
{
	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}
}

