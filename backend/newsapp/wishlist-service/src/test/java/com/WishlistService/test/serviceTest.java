package com.WishlistService.test;
import com.wishlistService.Entity.News;
import com.wishlistService.Entity.UserWishlist;
import com.wishlistService.exception.UserNotFoundException;
import com.wishlistService.repository.NewsRepo;
import com.wishlistService.repository.UserWishlistRepo;
import com.wishlistService.service.UserWishlistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UserWishlistServiceImplTest {

@Mock
private UserWishlistRepo userWishlistRepo;

@Mock
private NewsRepo newsRepo;

@InjectMocks
private UserWishlistServiceImpl userWishlistService;

private UserWishlist mockUserWishlist;

@BeforeEach
void setUp() {
	MockitoAnnotations.initMocks(this);
	mockUserWishlist = createMockUserWishlist();
}

private UserWishlist createMockUserWishlist() {
	UserWishlist wishlist = new UserWishlist();
	wishlist.setUseremail("test@example.com");
	return wishlist;
}

@Test
void testAddNews() {
	when(userWishlistRepo.findById("test@example.com")).thenReturn(Optional.empty());
	when(userWishlistRepo.save(any(UserWishlist.class))).thenReturn(mockUserWishlist);
	
	UserWishlist result = userWishlistService.addNews(mockUserWishlist);
	
	assertNotNull(result);
	assertEquals("test@example.com", result.getUseremail());
}

@Test
void testFindByWishlistId() {
	when(userWishlistRepo.findById("test@example.com")).thenReturn(Optional.of(mockUserWishlist));
	
	UserWishlist result = userWishlistService.findByWishlistId("test@example.com");
	
	assertNotNull(result);
	assertEquals("test@example.com", result.getUseremail());
}

@Test
void testFindByWishlistIdNotFound() {
	when(userWishlistRepo.findById("test@example.com")).thenReturn(Optional.empty());
	
	assertThrows(UserNotFoundException.class, () -> {
		userWishlistService.findByWishlistId("test@example.com");
	});
}

@Test
void testRemoveNews() {
	News news = new News();
	news.setId(1L);
	mockUserWishlist.setNews(new ArrayList<>());
	mockUserWishlist.getNews().add(news);
	
	when(newsRepo.findById(1L)).thenReturn(Optional.of(news));
	when(userWishlistRepo.findById("test@example.com")).thenReturn(Optional.of(mockUserWishlist));
	doNothing().when(newsRepo).delete(news);
	
	UserWishlist result = userWishlistService.removeNews("test@example.com", 1L);
	
	assertNotNull(result);
	assertEquals("test@example.com", result.getUseremail());
	
}

@Test
void testRemoveNewsNotFound() {
	when(newsRepo.findById(1L)).thenReturn(Optional.empty());
	
	assertThrows(UserNotFoundException.class, () -> {
		userWishlistService.removeNews("test@example.com", 1L);
	});
}
}
