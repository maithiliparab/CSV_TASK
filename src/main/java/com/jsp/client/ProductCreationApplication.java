package com.jsp.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ProductCreationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCreationApplication.class, args);
		System.out.println("working.....");
	}

}
