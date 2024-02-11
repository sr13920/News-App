package com.wishlistService.controller;

import com.wishlistService.Entity.News;
import com.wishlistService.Entity.UserWishlist;
import com.wishlistService.service.UserWishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
public class UserWishlistController {

private UserWishlistService wishlistservice;
public UserWishlistController(UserWishlistService wishlistservice) {
	super();
	this.wishlistservice = wishlistservice;
}

@GetMapping("/fetchedNews")
public List<News> getLatestNews() {
	return wishlistservice.getLatestHeadlines();
	
}

@GetMapping("/working")
public String working(){
	return "working";
}

@PostMapping("/addNews")
public ResponseEntity<?> addNews(@RequestBody UserWishlist wishlist) {
	try {
		UserWishlist updatedWishlist = wishlistservice.addNews(wishlist);
		return new ResponseEntity<>(updatedWishlist, HttpStatus.CREATED);
	} catch (IllegalArgumentException e) {
		return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
	}
}


@DeleteMapping("/removeNews/{wishlistId}/{newsId}")
public ResponseEntity<UserWishlist> removeNews(@PathVariable String wishlistId,@PathVariable Long newsId) {
	return new ResponseEntity<>(wishlistservice.removeNews(wishlistId,newsId), HttpStatus.OK);
	
}

@GetMapping("/getNews/{wishlistId}")
public ResponseEntity<UserWishlist> getNews(@PathVariable String wishlistId) {
	return new ResponseEntity<>(wishlistservice.findByWishlistId(wishlistId), HttpStatus.OK);
}
}
