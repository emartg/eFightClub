package com.urjcdad.efightclub.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //(exclude = {DataSourceAutoConfiguration.class })

public class EFightClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(EFightClubApplication.class, args);
	}

}
