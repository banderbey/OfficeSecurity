package com.example.officesecurity.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class CsvUserDAO {
    private String userName;
    private boolean loggedIn;
    private Integer id;
}
