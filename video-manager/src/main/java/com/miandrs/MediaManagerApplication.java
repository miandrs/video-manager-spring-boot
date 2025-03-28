package com.miandrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@SpringBootApplication
public class MediaManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaManagerApplication.class, args);
	}
	
	@Bean
	public Hibernate5Module dataTypeHibernateModule() {
		return new Hibernate5Module();
	}
}
