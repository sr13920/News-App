package com.AuthService.service;

import com.AuthService.model.UserInfo;

public interface UserService {


UserInfo login(String email, String password);
UserInfo deleteUser(String email);
}
