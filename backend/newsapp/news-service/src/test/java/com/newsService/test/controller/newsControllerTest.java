package com.newsService.test.controller;

import com.newsService.controller.NewsController;
import com.newsService.entity.News;
import com.newsService.entity.ResponseWrapper;
import com.newsService.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class newsControllerTest {
private MockMvc mockMvc;

@Mock
private NewsService newsService;

@InjectMocks
private NewsController newsController;

@BeforeEach
void setUp() {
	MockitoAnnotations.initMocks(this);
	mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();
}

@Test
public void testGetLatestNews() throws Exception {
	when(newsService.getLatestHeadlines(anyString(), anyBoolean()))
			.thenReturn(new ResponseWrapper(Arrays.asList(new News("Sample Title", "Sample Description"))));
	
	mockMvc.perform(MockMvcRequestBuilders.get("/country-news")
			                .param("fromCountry", "USA")
			                .param("onlyInternational", "true"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.news[0].Title").value("Sample Title"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.news[0].Description").value("Sample Description"));
}
}
