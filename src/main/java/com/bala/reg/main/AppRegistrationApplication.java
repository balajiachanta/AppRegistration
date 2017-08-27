package com.bala.reg.main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@ComponentScan("com.bala")
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages="com.bala.reg.db.repo")
@EntityScan(basePackages="com.bala.reg.db.table")
@PropertySource("classpath:/local/db.properties")
public class AppRegistrationApplication {

	private final Logger log = LoggerFactory.getLogger(AppRegistrationApplication.class);
	
	public static void main(String[] args) {

		
		SpringApplication.run(AppRegistrationApplication.class, args);
	}
	
	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}
}
