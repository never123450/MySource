package com.xwy.multipleDataSource.test01.service;

import com.xwy.multipleDataSource.test01.mapper.UserMapperTest01;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/*
 多数据源事务注意事项
在多数据源的情况下，使用@Transactional注解时，应该指定事务管理者
@Transactional(transactionManager = "test2TransactionManager")
 */
import lombok.extern.slf4j.Slf4j;

/**
 * UserServiceTest01<br>
 */
@Service
@Slf4j
public class UserServiceTest01 {
	@Autowired
	private UserMapperTest01 userMapperTest01;

//	@Transactional(transactionManager = "test1TransactionManager")
	public int insertUser(String name, Integer age) {
		int insertUserResult = userMapperTest01.insert(name, age);
		log.info("######insertUserResult:{}##########", insertUserResult);
		int i = 1 / age;
		// 怎么样验证事务开启成功!~
		return insertUserResult;
	}

}
