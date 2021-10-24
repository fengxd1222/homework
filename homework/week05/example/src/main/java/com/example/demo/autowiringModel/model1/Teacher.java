package com.example.demo.autowiringModel.model1;

import org.springframework.stereotype.Component;

@Component
public class Teacher implements Person {
    @Override
    public String identity() {
        return "teacher";
    }
}
