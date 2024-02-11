package com.userService.Test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.google.gson.Gson;
import com.userService.entity.User;
import com.userService.exception.UserAlreadyExistsException;
import com.userService.exception.UserNotFoundException;
import com.userService.repository.UserRepo;
import com.userService.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;


@SpringBootTest
public class UserServiceImplTest {
@Mock
private UserRepo userRepo;

@Mock
private KafkaTemplate<String, String> kafkaTemplate;

@Mock
private Gson gson;

@InjectMocks
private UserServiceImpl userService;

private User existingUser;
private User updatedUser;

@BeforeEach
void setUp() {
	existingUser = new User(1234567890, "John", "Doe", "john@example.com", "password");
	updatedUser = new User(1234567890, "UpdatedJohn", "UpdatedDoe", "john@example.com", "Updatedpassword");
}


@Test
void saveUser_UserAlreadyExistsException() {
	Mockito.when(userRepo.findByUseremail(existingUser.getUseremail())).thenReturn(existingUser);
	
	assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(existingUser));
	
	Mockito.verify(userRepo, Mockito.never()).save(any(User.class));
	Mockito.verify(kafkaTemplate, Mockito.never()).send(eq("user-register"), any(String.class));
}

@Test
void updateUser_Success() {
	Mockito.when(userRepo.findByUseremail(existingUser.getUseremail())).thenReturn(existingUser);
	Mockito.when(userRepo.save(any(User.class))).thenReturn(updatedUser);
	
	User resultUser = userService.updateUser(updatedUser);
	
	assertNotNull(resultUser);
	assertEquals(updatedUser.getFirstname(), resultUser.getFirstname());
	assertEquals(updatedUser.getLastname(), resultUser.getLastname());
	assertEquals(updatedUser.getPassword(), resultUser.getPassword());
	
	Mockito.verify(userRepo, Mockito.times(1)).findByUseremail(existingUser.getUseremail());
	Mockito.verify(userRepo, Mockito.times(1)).save(any(User.class));
}

@Test
void updateUser_UserNotFoundException() {
	Mockito.when(userRepo.findByUseremail(existingUser.getUseremail())).thenReturn(null);
	
	assertThrows(UserNotFoundException.class, () -> userService.updateUser(existingUser));
	
	Mockito.verify(userRepo, Mockito.never()).save(any(User.class));
}

@Test
void deleteUser_Success() {
	Mockito.when(userRepo.findByUseremail(existingUser.getUseremail())).thenReturn(existingUser);
	Mockito.doNothing().when(userRepo).deleteById(existingUser.getUseremail());
	
	User deletedUser = userService.deleteUser(existingUser.getUseremail());
	
	assertNotNull(deletedUser);
	assertEquals(existingUser.getUseremail(), deletedUser.getUseremail());
	
	Mockito.verify(userRepo, Mockito.times(1)).findByUseremail(existingUser.getUseremail());
	Mockito.verify(userRepo, Mockito.times(1)).deleteById(existingUser.getUseremail());
}

@Test
void deleteUser_UserNotFoundException() {
	Mockito.when(userRepo.findByUseremail(existingUser.getUseremail())).thenReturn(null);
	
	assertThrows(UserNotFoundException.class, () -> userService.deleteUser(existingUser.getUseremail()));
	
	Mockito.verify(userRepo, Mockito.never()).deleteById(existingUser.getUseremail());
}

@Test
void findByUseremail_Success() {
	Mockito.when(userRepo.findByUseremail(existingUser.getUseremail())).thenReturn(existingUser);
	
	User foundUser = userService.findByUseremail(existingUser.getUseremail());
	
	assertNotNull(foundUser);
	assertEquals(existingUser.getUseremail(), foundUser.getUseremail());
	
	Mockito.verify(userRepo, Mockito.times(2)).findByUseremail(existingUser.getUseremail());
}

@Test
void findByUseremail_UserNotFoundException() {
	String userEmail = "john.doe@example.com";
	
	Mockito.when(userRepo.findByUseremail(userEmail)).thenReturn(null);
	
	assertThrows(UserNotFoundException.class, () -> userService.findByUseremail(userEmail));
	
	Mockito.verify(userRepo, Mockito.times(1)).findByUseremail(userEmail);
}


//@Test
//void saveUser_Success() {
//	when(userRepo.findByUseremail(existingUser.getUseremail())).thenReturn(null);
//	when(userRepo.save(existingUser)).thenReturn(existingUser);
//	when(gson.toJson(existingUser)).thenReturn("jsonString"); // Mocking the gson.toJson()
//
//	User savedUser = userService.saveUser(existingUser);
//
//	assertNotNull(savedUser);
//	assertEquals(existingUser, savedUser);
//	verify(kafkaTemplate, times(1)).send(anyString(), eq("jsonString"));
//}

}
