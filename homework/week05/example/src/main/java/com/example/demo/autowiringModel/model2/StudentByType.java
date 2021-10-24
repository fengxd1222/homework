package com.example.demo.autowiringModel.model2;

import org.springframework.stereotype.Component;

//@Component
public class StudentByType implements PersonByType {
    @Override
    public String identity() {
        return "studentByType";
    }
}
