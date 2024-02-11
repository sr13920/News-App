package com.WishlistService.test;
import com.wishlistService.Entity.UserWishlist;
import com.wishlistService.controller.UserWishlistController;
import com.wishlistService.service.UserWishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserWishlistControllerTest {

@Mock
private UserWishlistService wishlistService;

@InjectMocks
private UserWishlistController wishlistController;

@BeforeEach
void setUp() {
	MockitoAnnotations.initMocks(this);
}

@Test
void testAddNews() {
	UserWishlist wishlist = new UserWishlist(); // Create a sample UserWishlist object
	
	// Mock the behavior of wishlistService.addNews()
	when(wishlistService.addNews(any(UserWishlist.class))).thenReturn(wishlist);
	
	ResponseEntity<UserWishlist> responseEntity = (ResponseEntity<UserWishlist>) wishlistController.addNews(wishlist);
	
	assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	assertEquals(wishlist, responseEntity.getBody());
}

@Test
void testRemoveNews() {
	String wishlistId = "wishlist123";
	Long newsId = 123L;
	
	UserWishlist wishlist = new UserWishlist(); // Create a sample UserWishlist object
	
	// Mock the behavior of wishlistService.removeNews()
	when(wishlistService.removeNews(wishlistId, newsId)).thenReturn(wishlist);
	
	ResponseEntity<UserWishlist> responseEntity = wishlistController.removeNews(wishlistId, newsId);
	
	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	assertEquals(wishlist, responseEntity.getBody());
}

@Test
void testGetNews() {
	String wishlistId = "wishlist123";
	
	UserWishlist wishlist = new UserWishlist(); // Create a sample UserWishlist object
	
	// Mock the behavior of wishlistService.findByWishlistId()
	when(wishlistService.findByWishlistId(wishlistId)).thenReturn(wishlist);
	
	ResponseEntity<UserWishlist> responseEntity = wishlistController.getNews(wishlistId);
	
	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	assertEquals(wishlist, responseEntity.getBody());
}
}
