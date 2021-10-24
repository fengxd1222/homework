package com.fengxudong.api.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 过滤器初始化类
 * 设置为单例模式
 */
@Configuration
public class FilterInitializer {

    @Autowired
    private List<HttpRequestFilter> httpRequestFilters;

    @Autowired
    private List<HttpResponseFilter> httpResponseFilters;

    public FilterInitializer(){

    }

    @PostConstruct
    public void sort(){
        initRequestFilter();
        initResponseFilter();
    }
    private void initResponseFilter() {
        httpResponseFilters.sort(Comparator.comparing(HttpResponseFilter::order));
        System.out.println(httpResponseFilters);
    }

    private void initRequestFilter() {
        httpRequestFilters.sort(Comparator.comparing(HttpRequestFilter::order));
        System.out.println(httpRequestFilters);
    }


    public List<HttpRequestFilter> getHttpRequestFilters() {
        return httpRequestFilters;
    }

    public List<HttpResponseFilter> getHttpResponseFilters() {
        return httpResponseFilters;
    }
}
