package com.example.demo.logic.service;

import com.example.demo.logic.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestMapper testMapper;
    @Override
    public Object getById(Long id) {
        return testMapper.getById(id);
    }

    @Override
    public Object insert(Map data) {
        return testMapper.insert(data);
    }
}
