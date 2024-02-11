package com.userService.Test.controller;

import com.userService.controller.UserController;
import com.userService.entity.User;
import com.userService.exception.UserNotFoundException;
import com.userService.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

@InjectMocks
private UserController userController;

@Mock
private UserService userService;

@BeforeEach
void setUp() {
	MockitoAnnotations.initMocks(this);
}

@Test
void saveUser_NewUser_SuccessfullySaved() {
	User newUser = new User(9898989899L, "John", "Doe", "john.doe@example.com", "password");
	when(userService.saveUser(any(User.class))).thenReturn(newUser);
	ResponseEntity<User> responseEntity = userController.saveUser(newUser);

	assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	assertEquals(newUser, responseEntity.getBody());
	verify(userService, times(1)).saveUser(any(User.class));
}

@Test
void updateUser_ExistingUser_SuccessfullyUpdated() {
	String email= "updated.john.doe@example.com";
	User updatedUser = new User(9898989899L, "UpdatedJohn", "UpdatedDoe", "email", "password");
	when(userService.updateUser(any(User.class))).thenReturn(updatedUser);

	ResponseEntity<User> responseEntity = userController.updateUser(updatedUser, email);

	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	assertEquals(updatedUser, responseEntity.getBody());
	verify(userService, times(1)).updateUser(any(User.class));
}

@Test
void deleteUser_ExistingUser_SuccessfullyDeleted() {
	String email= "updated.john.doe@example.com";
	User deletedUser = new User(9898989899L, "John", "Doe", "john.doe@example.com", "password1");
	when(userService.deleteUser(email)).thenReturn(deletedUser);

	ResponseEntity<User> responseEntity = userController.deleteUser(email);

	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	assertEquals(deletedUser, responseEntity.getBody());
	verify(userService, times(1)).deleteUser(email);
}

@Test
void deleteUser_UserNotFoundException() {
	String email= "updated.john.doe@example.com";
	
	when(userService.deleteUser(email)).thenThrow(new UserNotFoundException("User not found"));

	assertThrows(UserNotFoundException.class, () -> userController.deleteUser(email));
	verify(userService, times(1)).deleteUser(email);
}

@Test
void findUserByUseremailTest_UserFound() {

	String userEmail = "test@example.com";
	User expectedUser = new User();
	when(userService.findByUseremail(userEmail)).thenReturn(expectedUser);
	ResponseEntity<User> response = userController.findUserByUseremail(userEmail);
	assertEquals(HttpStatus.OK, response.getStatusCode());
	assertEquals(expectedUser, response.getBody());
	verify(userService, times(1)).findByUseremail(userEmail);
}
}
