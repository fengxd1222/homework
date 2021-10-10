package io.github.kimmking.gateway.util;

import io.github.kimmking.gateway.filter.HttpRequestFilter;

import java.io.File;
import java.net.URL;
import java.util.*;

public class ReflectionUtil {

    public static List<Class<?>> getAllInterfaceImpl(String path,Class<?> target){
        List<Class<?>> impls = new ArrayList<>();
        try {
            File[] files = new File(path).listFiles();
            List<String> classPaths = new ArrayList<>();
            for (File file : files) {
                if(file.isDirectory()){
                    //遍历文件夹所有的class类
                    listClassPath(file.getName(),classPaths);
                }
            }

            for (String classpath : classPaths) {
                Class<?> classObject = Class.forName(classpath);
                if (classObject.getSuperclass() == null) continue; // 判断该对象的父类是否为null
                Set<Class<?>> interfaces = new HashSet<>(Arrays.asList(classObject.getInterfaces()));
                if (interfaces.contains(target)) {
                    impls.add(Class.forName(classObject.getName()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return impls;
    }

    public static void listClassPath(String fileName, List<String> classPaths) throws Exception {
        URL resource = ReflectionUtil.class.getClassLoader().getResource("./" + fileName.replaceAll("\\.", "/"));
        if (resource == null) throw new Exception("fileName is error");
        File[] files = new File(resource.getFile()).listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                listClassPath(fileName+"." + file.getName(),classPaths);
            }else{
                if(file.getName().contains(".class")){
                    classPaths.add(fileName + "." + file.getName().replaceAll(".class", ""));
                }
            }
        }
    }
}
