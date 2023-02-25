package com.urjcdad.efightclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = {"model","com.urjcdad.efightclub","repository","service"})
public class EFightClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(EFightClubApplication.class, args);
	}

}
