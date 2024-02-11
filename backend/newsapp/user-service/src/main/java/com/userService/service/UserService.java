package com.userService.service;

import com.userService.entity.User;

public interface UserService {
User saveUser(User user);

User updateUser(User user);

User deleteUser(String email);

User findByUseremail(String useremail);


}

	

