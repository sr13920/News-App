package com.AuthService.service;

import com.AuthService.exception.UserNotFoundException;
import com.AuthService.model.UserInfo;
import com.AuthService.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {

@Autowired
private UserRepo repo;

@Override
public UserInfo login(String email, String password) {
	
	Optional<UserInfo> userinfo = repo.findByUseremailAndPassword(email, password);
	if (userinfo.isPresent())
		return userinfo.get();
	else
		return null;
}

@Override
public UserInfo deleteUser(String email) {
	// logger.info(" Executing deleteUser");
	Optional<UserInfo> user1 = repo.findById(email);
	if (user1.isPresent()) {
		repo.deleteById(email);
		return user1.get();
	} else {
		throw new UserNotFoundException("User not found");
	}
}

public UserInfo registerUser(UserInfo userdata) {
	Optional<UserInfo> userinfo = repo.findById(userdata.getUseremail());
	if (userinfo.isEmpty()) {
		return repo.save(userdata);
		
	} else
		return null;
}


}
