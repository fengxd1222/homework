package com.example.demo.autowiringModel.model1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 默认情况 非自动状态，根据ref元素装配（先通过bean的类型。即person接口找寻他的实现类，当发现多个时，再根据beanname）
 */
@Component
public class TestBean {
    @Autowired
    Person student;

    @PostConstruct
    public void showIdentity(){
        System.out.println(student.identity());
    }
}
