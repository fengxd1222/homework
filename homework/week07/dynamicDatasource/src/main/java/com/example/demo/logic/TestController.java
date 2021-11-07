package com.example.demo.logic;

import com.example.demo.logic.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/get")
    public Object get(@RequestParam(name = "id")Long id){
        return testService.getById(id);
    }

    @PostMapping
    public Object insert(@RequestBody Map data){
        return testService.insert(data);
    }
}
