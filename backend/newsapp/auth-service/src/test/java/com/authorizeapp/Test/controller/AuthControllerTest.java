package com.authorizeapp.Test.controller;

import com.AuthService.controller.AuthController;
import com.AuthService.exception.UserNotFoundException;
import com.AuthService.model.UserInfo;
import com.AuthService.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AuthControllerTest {


@Mock
private UserService userService;

@InjectMocks
private AuthController authController;

@BeforeEach
void setUp() {
	MockitoAnnotations.openMocks(this);
}
@Test
void testLoginWithValidCredentials() throws UserNotFoundException {
	UserInfo userInfo = new UserInfo("valid@example.com", "password");
	
	when(userService.login(userInfo.getUseremail(), userInfo.getPassword())).thenReturn(userInfo);
	
	ResponseEntity<?> responseEntity = authController.login(userInfo);
	
	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	assertTrue(responseEntity.getBody() instanceof Map);
//
//	Map<?, ?> responseBody = (Map<?, ?>) responseEntity.getBody();
//	assertNotNull(responseBody.get("yourTokenKey"));
}

@Test
void testLoginWithInvalidCredentials() throws UserNotFoundException {
	UserInfo userInfo = new UserInfo("kav@gmail.com", "kav@123");
	
	when(userService.login(anyString(), anyString())).thenReturn(null);
	
	ResponseEntity<?> responseEntity = authController.login(userInfo);
	
	assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	assertEquals("Invalid user", responseEntity.getBody());
}

@Test
void testLoginWithNullCredentials() {
	
	UserInfo userInfo = new UserInfo(null, null);
	
	
	assertThrows(UserNotFoundException.class, () -> authController.login(userInfo));
}
}

