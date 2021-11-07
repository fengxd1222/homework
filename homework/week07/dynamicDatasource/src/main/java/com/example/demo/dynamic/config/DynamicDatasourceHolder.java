package com.example.demo.dynamic.config;

import java.util.ArrayList;
import java.util.List;

public class DynamicDatasourceHolder {
    private static final ThreadLocal<String> contextHolders = new ThreadLocal<String>();

    static final List<String> datasourceName = new ArrayList<>();

    public static void setDatasourceType(String typeName){
        contextHolders.set(typeName);
    }

    public static String getDatasourceType(){
        return contextHolders.get();
    }

    public static void remove(){
        contextHolders.set(null);
        contextHolders.remove();
    }

    public static Boolean containType(String name){
        return datasourceName.contains(name);
    }

}
