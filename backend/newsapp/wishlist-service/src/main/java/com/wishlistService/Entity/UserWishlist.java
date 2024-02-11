package com.wishlistService.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wishlists")
public class UserWishlist {
@Id
@JsonProperty("useremail")
private String useremail;

@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name = "email_fk",referencedColumnName = "useremail")
private List<News> news;

}

