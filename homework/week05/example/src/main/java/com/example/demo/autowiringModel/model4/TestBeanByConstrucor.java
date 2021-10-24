package com.example.demo.autowiringModel.model4;

import org.springframework.stereotype.Component;

@Component
public class TestBeanByConstrucor {

    public TestBeanByConstrucor(PersonByConstrucor teacherByConstrucor) {
        System.out.println("TestBeanByConstrucor "+teacherByConstrucor.identity());
    }
}
