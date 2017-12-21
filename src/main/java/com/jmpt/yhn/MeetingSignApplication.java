package com.jmpt.yhn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan(basePackages = "com.jmpt.yhn.entity.mapper")
public class MeetingSignApplication {
	public static void main(String[] args) {
		SpringApplication.run(MeetingSignApplication.class, args);
	}
}
