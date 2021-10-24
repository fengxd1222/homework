package com.example.demo.autowiringModel.model2;

import org.springframework.stereotype.Component;

@Component
public class TeacherByType implements  PersonByType {
    @Override
    public String identity() {
        return "teacherByType";
    }
}
