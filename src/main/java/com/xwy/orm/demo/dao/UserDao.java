package com.xwy.orm.demo.dao;

import com.xwy.orm.demo.model.User;
import com.xwy.orm.framework.BaseDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Repository
public class UserDao extends BaseDaoSupport<User,Integer> {

    @Override
    protected String getPKColumn() {return "id";}

    @Resource(name="dynamicDataSource")
    protected void setDataSource(DataSource dataSource) {

    }
}
