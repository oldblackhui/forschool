package com.cumt.forschool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.cumt.forschool.mapper")
@EnableTransactionManagement
public class ForschoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForschoolApplication.class, args);
	}

}
