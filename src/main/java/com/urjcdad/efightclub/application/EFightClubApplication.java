package com.urjcdad.efightclub.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication //(exclude = {DataSourceAutoConfiguration.class })
//@ComponentScan(basePackages = {"com.urjcdad.model","com.urjcdad.efightclub","com.urjcdad.repository","com.urjcdad.service"})

public class EFightClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(EFightClubApplication.class, args);
	}

}
