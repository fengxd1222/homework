package com.example.demo.autowiringModel.model3;

import org.springframework.stereotype.Component;

/**
 * byName的自动装配：
 *  set方法的名称需要和要注入bean的name一致，才能注入成功
 *  同样和byType一样，并不需要显示的使用@Autowired注解
 */
@Component
public class TestBeanByName {

    public void setStudentByName(PersonByName studentByName){
        System.out.println("TestBeanByName :"+studentByName.identity());
    }
}
