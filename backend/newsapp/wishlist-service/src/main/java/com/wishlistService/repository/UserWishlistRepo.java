package com.wishlistService.repository;

import com.wishlistService.Entity.UserWishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWishlistRepo extends JpaRepository<UserWishlist, String> {

}
