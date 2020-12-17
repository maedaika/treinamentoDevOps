package com.vindixit.foton.supporttables;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SupporttablesApplication {
	
	  //Para realizar o deploy em qualquer servidor alem do TOMCAT embargado 
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
	    return builder.sources(SupporttablesApplication.class);
	  }

	public static void main(String[] args) {
		SpringApplication.run(SupporttablesApplication.class, args);
	}

}
