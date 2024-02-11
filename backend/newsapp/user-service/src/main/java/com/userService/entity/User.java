package com.userService.entity;

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
@Table(name = "Users")
public class User {


@Column(name = "Mobile_no", nullable = false)
private long mobile;
@Column(name = "first_name", nullable = false)
private String firstname;
@Column(name = "last_name")
private String lastname;

@Id
@Column(name = "useremail")
private String useremail;

private String password;

}
