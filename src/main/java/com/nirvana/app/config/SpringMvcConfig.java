package com.nirvana.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ImportResource("classpath:spring-jpa.xml")
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages="com.nirvana.dal.api")
public class SpringMvcConfig {

}
