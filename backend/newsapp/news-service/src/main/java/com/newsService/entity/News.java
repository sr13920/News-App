package com.newsService.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityScan
public class News {

@JsonProperty("Title")
private String Title;

@JsonProperty("Description")
private String Description;

@JsonProperty("Image")
private String Image;
}
