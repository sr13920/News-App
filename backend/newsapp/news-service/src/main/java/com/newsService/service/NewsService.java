package com.newsService.service;

import com.newsService.entity.News;
import com.newsService.entity.ResponseWrapper;

import java.util.List;

public interface NewsService {
ResponseWrapper getLatestHeadlines(String fromCountry, String language);
List<News> fetchedNews();
}
