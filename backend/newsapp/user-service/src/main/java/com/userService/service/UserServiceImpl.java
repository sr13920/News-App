package com.userService.service;

import com.userService.entity.User;
import com.userService.exception.UserAlreadyExistsException;
import com.userService.exception.UserNotFoundException;
import com.userService.repository.UserRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


private static final String TOPIC = "user-register";
@Autowired
KafkaTemplate<String, String> kafkaTemplate;
@Autowired
private Gson gson;
private UserRepo userRepo;

@Autowired
public UserServiceImpl(UserRepo userRepo) {
	this.userRepo = userRepo;
}

@Override
public User saveUser(User user) {
	// logger.info(" Executing Save User");
	Optional<User> user1 = Optional.ofNullable(userRepo.findByUseremail(user.getUseremail()));
	if (user1.isPresent()) {
		throw new UserAlreadyExistsException("User Already Exists");
	} else {
		User users = userRepo.save(user);
		kafkaTemplate.send(TOPIC, gson.toJson(user));
		return users;
	}
	
}

@Override
public User updateUser(User user) {
	// logger.info(" Executing UdatUser");
	Optional<User> user1 = Optional.ofNullable(userRepo.findByUseremail(user.getUseremail()));
	if (user1.isPresent()) {
		User tempuser = user1.get();
		tempuser.setFirstname(user.getFirstname());
		tempuser.setLastname(user.getLastname());
		tempuser.setUseremail(user.getUseremail());
		tempuser.setPassword(user.getPassword());
		return userRepo.save(tempuser);
	} else {
		throw new UserNotFoundException("User not Found");
	}
}

@Override
public User deleteUser(String email) {
	// logger.info(" Executing deleteUser");
	Optional<User> user1 = Optional.ofNullable(userRepo.findByUseremail(email));
	if (user1.isPresent()) {
		userRepo.deleteById(email);
		kafkaTemplate.send("user-delete", email);
		return user1.get();
	} else {
		throw new UserNotFoundException("User not found");
	}
}

@Override
public User findByUseremail(String email) {
	// logger.info(" Executing getUser");
	Optional<User> user1 = Optional.ofNullable(userRepo.findByUseremail(email));
	if (user1.isPresent()) {
		return userRepo.findByUseremail(email);
	} else {
		throw new UserNotFoundException("User not found");
	}
}

}
