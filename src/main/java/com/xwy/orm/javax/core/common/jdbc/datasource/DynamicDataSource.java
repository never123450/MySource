package com.xwy.orm.javax.core.common.jdbc.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @param
 * @author xwy
 * @Description 动态数据源
 * @date 2021/3/31
 * @return
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    //entry的目的，主要是用来给每个数据源打个标记
    private DynamicDataSourceEntry dataSourceEntry;

    @Override
    protected Object determineCurrentLookupKey() {
        return this.dataSourceEntry.get();
    }

    public void setDataSourceEntry(DynamicDataSourceEntry dataSourceEntry) {
        this.dataSourceEntry = dataSourceEntry;
    }

    public DynamicDataSourceEntry getDataSourceEntry() {
        return this.dataSourceEntry;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
