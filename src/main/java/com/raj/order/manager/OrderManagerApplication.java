package com.raj.order.manager;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagerApplication.class, args);
	}
	
	/*@Bean
	public DataSource database() {
	    return DataSourceBuilder.create()
	        .url("jdbc:mysql://127.0.0.1:3306/activiti-spring-boot?characterEncoding=UTF-8")
	        .username("root")
	        .password("root")
	        .driverClassName("com.mysql.jdbc.Driver")
	        .build();
	}*/
	
	@Bean
	InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {

	    return new InitializingBean() {
	        public void afterPropertiesSet() throws Exception {

	            Group group = identityService.newGroup("user");
	            group.setName("users");
	            group.setType("security-role");
	            identityService.saveGroup(group);

	            User admin = identityService.newUser("admin");
	            admin.setPassword("admin");
	            identityService.saveUser(admin);
	        }
	    };
	}
}
