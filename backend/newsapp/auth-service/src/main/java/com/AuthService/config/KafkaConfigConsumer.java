package com.AuthService.config;

import com.AuthService.model.UserInfo;
import com.AuthService.service.UserServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@EnableKafka
public class KafkaConfigConsumer {

@Autowired
Gson gson;

@Autowired
UserServiceImpl authUserService;

@KafkaListener(topics = "user-register", groupId = "news")
public void consume(String users) {
	System.out.println("received message=" + users);
	UserInfo userdata = gson.fromJson(users, UserInfo.class);
	UserInfo result = authUserService.registerUser(userdata);
	
	System.out.println("Stored data in User Copy" + userdata.toString());
	
}

@KafkaListener(topics = "user-delete", groupId = "news")
public void deleteUser(String email) {
	System.out.println("received message=" + email);
	
	UserInfo userInfo = authUserService.deleteUser(email);
	
	System.out.println("deleted data in auth" + userInfo.toString());
	
}
	
}
