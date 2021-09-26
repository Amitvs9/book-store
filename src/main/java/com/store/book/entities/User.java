package com.store.book.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * User
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @NotEmpty
    @Size(min= 5,message = "User name should be at least 5 characters long")
    @Column(name = "userName")
    private String userName;
    @NotEmpty
    @Size(min= 8,message = "Password name should be at least 8 characters long")
    private String password;

}
