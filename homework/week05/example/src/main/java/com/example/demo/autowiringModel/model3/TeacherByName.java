package com.example.demo.autowiringModel.model3;

import org.springframework.stereotype.Component;

@Component
public class TeacherByName implements PersonByName {
    @Override
    public String identity() {
        return "TeacherByName";
    }
}
