package com.example.officesecurity.model;

import lombok.Data;

@Data
public class CustomResponseEntity {
    private final String ok = "passed";
    private final String notOK = "something went wrong";
}
