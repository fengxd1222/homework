package com.example.demo.dynamic.aspect;

import com.example.demo.dynamic.config.DynamicDatasourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;

public class DynamicDatasourceAspect {
    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, DBSelect ds) throws Throwable {
        String dsName = ds.name();
        if (!DynamicDatasourceHolder.containType(dsName)) {
           DynamicDatasourceHolder.setDatasourceType("master");
        } else {
            DynamicDatasourceHolder.setDatasourceType(dsName);
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, DBSelect ds) {
        DynamicDatasourceHolder.remove();
    }
}
