package com.example.demo.dynamic.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDatasource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = DynamicDatasourceHolder.getDatasourceType();
        return dataSourceName;
    }
}
