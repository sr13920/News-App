package com.wishlistService.repository;

import com.wishlistService.Entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepo extends JpaRepository<News, Long> {
}
