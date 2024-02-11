package com.wishlistService.service;


import com.wishlistService.Entity.News;
import com.wishlistService.Entity.UserWishlist;
import org.apache.catalina.User;

import java.util.List;


public interface UserWishlistService {

UserWishlist addNews(UserWishlist wishlist);

UserWishlist removeNews(String id, Long newsId);

UserWishlist findByWishlistId(String id);

List<News> getLatestHeadlines();

}
