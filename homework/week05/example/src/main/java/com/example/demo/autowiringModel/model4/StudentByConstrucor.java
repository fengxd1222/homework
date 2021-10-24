package com.example.demo.autowiringModel.model4;

import org.springframework.stereotype.Component;

@Component
public class StudentByConstrucor implements PersonByConstrucor {
    @Override
    public String identity() {
        return "StudentByConstrucor";
    }
}
