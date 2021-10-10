package io.github.kimmking.gateway.filter;

import io.github.kimmking.gateway.NettyServerApplication;
import io.github.kimmking.gateway.util.ReflectionUtil;


import java.util.*;

/**
 * 过滤器初始化类
 * 设置为单例模式
 */
public class FilterInitializer {
    //扫描的包路径 后期可以改成配置文件的方式，现在以启动类所在的路径
    private String path;
    private final List<HttpRequestFilter> httpRequestFilters = new ArrayList<>();

    private final List<HttpResponseFilter> httpResponseFilters = new ArrayList<>();

    private FilterInitializer(){
        this.path = NettyServerApplication.class.getClassLoader().getResource("").getPath();
        init();
    }

    private void init(){
        initRequestFilter();
        initResponseFilter();
    }

    private void initResponseFilter() {
        List<Class<?>> allInterfaceImpl = ReflectionUtil.getAllInterfaceImpl(path, HttpResponseFilter.class);
        if (allInterfaceImpl != null) {
            for (Class<?> aClass : allInterfaceImpl) {
                try {
                    HttpResponseFilter httpResponseFilter = (HttpResponseFilter) aClass.newInstance();
                    httpResponseFilters.add(httpResponseFilter);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        //对整个filter进行一次排序
        httpResponseFilters.sort(Comparator.comparing(HttpResponseFilter::order));
        System.out.println(httpResponseFilters);
    }

    private void initRequestFilter() {
        List<Class<?>> allInterfaceImpl = ReflectionUtil.getAllInterfaceImpl(path, HttpRequestFilter.class);
        if (allInterfaceImpl != null) {
            for (Class<?> aClass : allInterfaceImpl) {
                try {
                    HttpRequestFilter httpRequestFilter = (HttpRequestFilter) aClass.newInstance();
                    httpRequestFilters.add(httpRequestFilter);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        //对整个filter进行一次排序
        httpRequestFilters.sort(Comparator.comparing(HttpRequestFilter::order));
        System.out.println(httpRequestFilters);
    }

    private static class SingletonFilterInitializer{
        private static final FilterInitializer FILTER_INITIALIZER = new FilterInitializer();
    }

    public static FilterInitializer getInstance(){
        return SingletonFilterInitializer.FILTER_INITIALIZER;
    }

    public List<HttpRequestFilter> getHttpRequestFilters() {
        return httpRequestFilters;
    }

    public List<HttpResponseFilter> getHttpResponseFilters() {
        return httpResponseFilters;
    }
}
