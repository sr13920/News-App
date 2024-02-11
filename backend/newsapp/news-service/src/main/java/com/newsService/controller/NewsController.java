package com.newsService.controller;

import com.newsService.entity.News;
import com.newsService.entity.ResponseWrapper;
import com.newsService.service.NewsService;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
//@SecurityRequirement(name = "Bearer Authentication")
public class NewsController {
private final NewsService newsService;

public NewsController(NewsService newsService) {
	this.newsService = newsService;
}

@GetMapping("/country-news")
public ResponseWrapper getLatestNews(@RequestParam String fromCountry, @RequestParam String language) {
	return newsService.getLatestHeadlines(fromCountry, language);

}

@GetMapping("/fetchedNews")
public List<News> fetchedNews() {
	System.out.println("fetch news hit");
	return newsService.fetchedNews();
}
}
