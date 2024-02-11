package com.userService.controller;

import com.userService.entity.User;
import com.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
public class UserController {

@Autowired
private UserService userService;


//add user
@PostMapping("/saveUser")
public ResponseEntity<User> saveUser(@RequestBody User user) {
	return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
}

//Update User
@PutMapping("/updateUser/{id}")
public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") String id) {
	
	return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
}

@DeleteMapping("/deleteUser/{id}")
public ResponseEntity<User> deleteUser(@PathVariable("id") String id) {
	
	return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
}

@GetMapping("/get/{email}")
public ResponseEntity<User> findUserByUseremail(@PathVariable("email") String useremail) {
	
	return new ResponseEntity<>(userService.findByUseremail(useremail), HttpStatus.OK);
}

	
}
