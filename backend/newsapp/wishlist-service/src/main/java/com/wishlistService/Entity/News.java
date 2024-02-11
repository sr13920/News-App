package com.wishlistService.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "News")
public class News {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

private Long Id;

@JsonProperty("Title")
private String Title;

@JsonProperty("Description")
private String Description;

@JsonProperty("Image")
private String Image;

}

