package com.newsService.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsService.entity.News;
import com.newsService.entity.ResponseWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
private static final String API_URL = "https://news67.p.rapidapi.com/v2/country-news";
private static final String API_KEY = "4f45804ac1msh1624a925ab4f273p1efea5jsnd8b69966b939";

private final RestTemplate restTemplate;
private final ObjectMapper objectMapper;
private List<News> newsData;

private String jsonData = "{\n" +
		                  "    \"news\": [\n" +
		                  "        {\n" +
		                  "            \"Title\": \"Mithun Chakraborty hospitalised in Kolkata\",\n" +
		                  "            \"Description\": \"Superstar Mithun Chakraborty and BJP leader Mithun Chakraborty have been admitted to a private hospital in Kolkata on Saturday after he complained of acute uneasiness.\",\n" +
		                  "            \"Image\": \"https://www.punjabnewsexpress.com/images/article/article239713.jpg\"\n" +
		                  "        },\n" +
		                  "        {\n" +
		                  "            \"Title\": \"Prioritise deployment of anti-drone technology on India-Pak border: MP Sahney\",\n" +
		                  "            \"Description\": \"Punjab Member of Parliament Vikramjit Singh Sahney on Saturday urged the Central government to prioritise the deployment of anti-drone technology to safeguard the youth from falling victim to drug addiction.\",\n" +
		                  "            \"Image\": \"https://www.punjabnewsexpress.com/images/article/article239724.jpg\"\n" +
		                  "        },\n" +
		                  "        {\n" +
		                  "            \"Title\": \"January 22 will be historic date for years to come, says Amit Shah\",\n" +
		                  "            \"Description\": \"Home Minister Amit Shah, while replying to the debate on Ram Temple construction & Ram Lalla’s consecration in Ayodhya, said that the Pran Pratistha day, January 22, will go down in the annals of history, for years to come.\",\n" +
		                  "            \"Image\": \"https://www.punjabnewsexpress.com/images/article/article239726.jpg\"\n" +
		                  "        },\n" +
		                  "        {\n" +
		                  "            \"Title\": \"'Fulfill your dream & shoot me': K'taka Cong MP tells BJP leader\",\n" +
		                  "            \"Description\": \"Karnataka Congress MP D. K. Suresh on Saturday said that the senior BJP leader K.S. Eshwarappa can fulfill his dream by shooting him dead.\",\n" +
		                  "            \"Image\": \"https://www.punjabnewsexpress.com/images/article/article239725.jpg\"\n" +
		                  "        },\n" +
		                  "        {\n" +
		                  "            \"Title\": \"AAP to announce candidates for all Lok Sabha seats in Punjab, Chandigarh\",\n" +
		                  "            \"Description\": \"Kejriwal made the announcement addressing a gathering organised for the\",\n" +
		                  "            \"Image\": \"https://cdn.telanganatoday.com/wp-content/uploads/2024/02/Kejriwal.jpg\"\n" +
		                  "        },\n" +
		                  "        {\n" +
		                  "            \"Title\": \"Marriage agencies going bankrupt in Japan as dating apps use surge\",\n" +
		                  "            \"Description\": \"A total of 11 marriage agencies filed for bankruptcy over the past year in the country, while another 11 closed, suspended operations or were dissolved\",\n" +
		                  "            \"Image\": \"https://cdn.telanganatoday.com/wp-content/uploads/2024/02/Japan-.jpg\"\n" +
		                  "        },\n" +
		                  "        {\n" +
		                  "            \"Title\": \"Apple reportedly working on foldable iPhone; Here’s what we know so far\",\n" +
		                  "            \"Description\": \"Apple is currently building prototypes of clamshell-style foldable iPhones which means they could look similar to the Galaxy Z Flip phones in design.\",\n" +
		                  "            \"Image\": \"https://cdn.telanganatoday.com/wp-content/uploads/2024/02/Apple.jpg\"\n" +
		                  "        },\n" +
		                  "        {\n" +
		                  "            \"Title\": \"BJP Tribal Morcha protest attempt foiled by police\",\n" +
		                  "            \"Description\": \"As soon as the morcha members started their 'Chalo Assembly ' rally, the police asked them to disburse as rallies cannot be taken out near the assembly.\",\n" +
		                  "            \"Image\": \"https://cdn.telanganatoday.com/wp-content/uploads/2022/02/cropped-android-chrome-512x512-1-267x267-2.png\"\n" +
		                  "        },\n" +
		                  "        {\n" +
		                  "            \"Title\": \"January 22 will be historic date for years to come, says Amit Shah\",\n" +
		                  "            \"Description\": \"Beginning his speech in Lok Sabha, Amit Shah said that today he wouldn’t reply to the Opposition’s charges and questions but rather speak his heart out on the ‘Rammaya’ atmosphere that the country is basking in.\",\n" +
		                  "            \"Image\": \"https://cdn.telanganatoday.com/wp-content/uploads/2024/02/Amit-Shah-.jpg\"\n" +
		                  "        },\n" +
		                  "        {\n" +
		                  "            \"Title\": \"Kamala Harris slams special counsel's claim about Biden's age, memory\",\n" +
		                  "            \"Description\": \"US Vice President Kamala Harris, trusted lieutenant and running mate of President Joe Biden in 2024 presidential elections, is using her outreach and clout with African Americans, Hispanics and Asians and Indians in tandem with the White House working overtime in damage control exercise to discredit a special counsel's claims that Biden suffers from memory loss.\",\n" +
		                  "            \"Image\": \"https://www.punjabnewsexpress.com/images/article/article239711.jpg\"\n" +
		                  "        }\n" +
		                  "    ]\n" +
		                  "}";


public NewsServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
	this.restTemplate = restTemplate;
	this.objectMapper = objectMapper
			                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	this.newsData = new ArrayList<>();
}

public List<News> fetchedNews(){
	return this.newsData;
}


@Override
public ResponseWrapper getLatestHeadlines(String fromCountry, String language) {
//	UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(API_URL)
//			                               .queryParam("fromCountry", fromCountry)
//			                               .queryParam("languages", language);
//
//	URI apiUrlWithParams = builder.build().toUri();
//	HttpHeaders headers = new HttpHeaders();
//	headers.add("X-RapidAPI-Key", API_KEY);
//	headers.add("X-RapidAPI-Host", "news67.p.rapidapi.com");
//	RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, apiUrlWithParams);
	try {
	//	String jsonResponse = restTemplate.exchange(requestEntity, String.class).getBody();
		
		ResponseWrapper responseWrapper = objectMapper.readValue(this.jsonData, ResponseWrapper.class);
		this.newsData.addAll(responseWrapper.getNews());
		return responseWrapper;
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Error during API request: " + e.getMessage());
	}
	
	return new ResponseWrapper();
}
}
