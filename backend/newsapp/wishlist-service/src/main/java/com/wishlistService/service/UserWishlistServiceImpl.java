package com.wishlistService.service;

import com.wishlistService.Entity.News;

import com.wishlistService.Entity.UserWishlist;
import com.wishlistService.exception.UserNotFoundException;
import com.wishlistService.repository.NewsRepo;
import com.wishlistService.repository.UserWishlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserWishlistServiceImpl implements UserWishlistService {

private final UserWishlistRepo userWishlistRepo;
private final NewsRepo newsRepo;
private final RestTemplate restTemplate;
public static final String NEWS_SERVICE="newsService";
private static final String BASEURL = "http://localhost:8087/news/fetchedNews";
private int attempt=1;
private News[] newsArray;


@CircuitBreaker(name = NEWS_SERVICE, fallbackMethod = "getAllAvailableNews")
//@Retry(name = NEWS_SERVICE, fallbackMethod = "getAllAvailableNews")
public List<News> getLatestHeadlines() {
	String url = BASEURL;
	this.newsArray = restTemplate.getForObject(url, News[].class);
	if (newsArray != null) {
		return Arrays.asList(newsArray);
	} else {
		System.out.println("Empty response");
		return Collections.emptyList();
	}
}



public List<News> getAllAvailableNews(Exception e){
	return Stream.of(
			new News(1L,"fisrt","first","first"),
			new News(1L,"fisrt","first","first")
	).collect(Collectors.toList());
	
}

@Autowired
public UserWishlistServiceImpl(UserWishlistRepo userWishlistRepo, NewsRepo newsRepo,RestTemplate restTemplate) {
	this.userWishlistRepo = userWishlistRepo;
	this.newsRepo = newsRepo;
	this.restTemplate=restTemplate;
}


@Override
public UserWishlist addNews(UserWishlist wishlist) {
	this.getLatestHeadlines();
	String userEmail = wishlist.getUseremail();
	Optional<UserWishlist> existingWishlistOptional = userWishlistRepo.findById(userEmail);
	if (existingWishlistOptional.isPresent()) {
		
		UserWishlist existingWishlist = existingWishlistOptional.get();
		News news=wishlist.getNews().get(0);
		Long count2= Arrays.stream(this.newsArray).filter(data->data.getTitle().toLowerCase().equals(news.getTitle().toLowerCase())).count();
		Long count =existingWishlist.getNews().stream().filter(data->data.getTitle().toLowerCase().equals(news.getTitle().toLowerCase())).count();
		if (count2 == 0) {
			throw new IllegalArgumentException("News not found");
		}
		if(count==0){
			if(news.getDescription().length()>255){
				news.setDescription(news.getDescription().substring(0,255));
			}
			existingWishlist.getNews().add(news);
			return userWishlistRepo.save(existingWishlist);
		}else{
			return existingWishlist;
		}
		
	} else {
		return userWishlistRepo.save(wishlist);
	}
}



@Override
public UserWishlist findByWishlistId(String wishlistId) {
	
	Optional<UserWishlist> wishlist1 = userWishlistRepo.findById(wishlistId);
	if (wishlist1.isPresent()) {
		return wishlist1.get();
	} else {
		throw new UserNotFoundException("Did not find News");
	}
}

@Override
public UserWishlist removeNews(String wishlistId,Long newsId) {
	Optional<News> news = newsRepo.findById(newsId);
	if (news.isPresent()) {
		newsRepo.delete(news.get());
		return userWishlistRepo.findById(wishlistId).get();
	} else {
		throw new UserNotFoundException("Did not find News");
	}
}
}