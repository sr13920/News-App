//package com.newsService.test.service;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.newsService.entity.News;
//import com.newsService.entity.ResponseWrapper;
//import com.newsService.service.NewsServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//
//public class newsSerivceTest {
//@Mock
//private RestTemplate restTemplate;
//
//@Mock
//private ObjectMapper objectMapper;
//
//@InjectMocks
//private NewsServiceImpl newsService;
//
//@BeforeEach
//void setUp() {
//	MockitoAnnotations.initMocks(this);
//	ReflectionTestUtils.setField(newsService, "API_URL", "https://news67.p.rapidapi.com/v2/country-news");
//	ReflectionTestUtils.setField(newsService, "API_KEY", "badb0ebf76msha3a655e517c532fp1d1a9bjsnf738c268cdfb");
//}
//
//@Test
//public void testGetLatestHeadlines() throws Exception {
//	when(restTemplate.exchange(any(RequestEntity.class), eq(String.class)))
//			.thenReturn(new ResponseEntity<>("{ \"news\": [{ \"Title\": \"Sample Title\", \"Description\": \"Sample Description\" }] }", HttpStatus.OK));
//
//	when(objectMapper.configure(eq(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES), eq(false))).thenReturn(objectMapper);
//	when(objectMapper.setSerializationInclusion(eq(JsonInclude.Include.NON_NULL))).thenReturn(objectMapper);
//
//	when(objectMapper.readValue(any(String.class), eq(ResponseWrapper.class)))
//			.thenReturn(new ResponseWrapper(Collections.singletonList(new News("Sample Title", "Sample Description"))));
//
//	// Call the actual service method
//	ResponseWrapper response = newsService.getLatestHeadlines("USA", true);
//
//	// Assertions
//	assertNotNull(response);
//	assertEquals(1, response.getNews().size());
//	News news = response.getNews().get(0);
//	assertEquals("Sample Title", news.getTitle());
//	assertEquals("Sample Description", news.getDescription());
//}
//}
