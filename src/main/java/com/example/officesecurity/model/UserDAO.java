package com.example.officesecurity.model;


import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity // This tells Hibernate to make a table out of this class

public class UserDAO {


    /* @UserExcessConstraint*/
    private String userName;
    private boolean loggedIn;
    // private String password;
    @Id
    @GeneratedValue
    private Integer id;
    //afaefadfsdfsdfsdfsdfdsf






}
