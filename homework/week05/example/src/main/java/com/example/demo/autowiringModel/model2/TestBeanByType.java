package com.example.demo.autowiringModel.model2;

import org.springframework.stereotype.Component;

/**
 * 将autowiringmodel改为2 byType之后，并不需要显示的使用@Autowird注解 注入bean
 * 只需要提供spring认可的两种DI注入方式：1、set注入（注解也是通过反射进行set注入的）；2、构造器
 * 但是因为此时PersonByType有两个实现类，byType当找到多个实现类的bean时，会抛出异常
 * 需要注释掉一个实现类
 */
@Component
public class TestBeanByType {

    public void setPersonByType(PersonByType teacher){
        System.out.println(teacher.identity());
    }
}
