package com.xwy.SpringXmlParse.service.Impl;

import com.xwy.SpringXmlParse.dao.UserDao;
import com.xwy.SpringXmlParse.service.LogService;
import com.xwy.SpringXmlParse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//user 服务层
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private LogService logService;
	// @Autowired
	// private TransactionUtils transactionUtils;

	// spring 事务封装呢？ aop技术
	// public void add() {
	// TransactionStatus transactionStatus = null;
	// try {
	// // 开启事务
	// transactionStatus = transactionUtils.begin();
	// userDao.add("test001", 20);
	// System.out.println("开始报错啦!@!!");
	// // int i = 1 / 0;
	// System.out.println("################");
	// userDao.add("test002", 21);
	// // 提交事务
	// if (transactionStatus != null)
	// transactionUtils.commit(transactionStatus);
	// } catch (Exception e) {
	// e.getMessage();
	// // 回滚事务
	// if (transactionStatus != null)
	// transactionUtils.rollback(transactionStatus);
	// }
	// }

	// 声明：@Transactional 或者XML方式
	// 方法执行开始执行前，开启提交事务
	// @ExtTransaction
	// public void add() {
	// try {
	// userDao.add("test001", 20);
	// int i = 1 / 0;
	// System.out.println("################" + i);
	// userDao.add("test002", 21);
	// } catch (Exception e) {
	// // 获取当前事务，手动进行回滚
	// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	// }
	// // begin commit/rollback
	// }
	//
	// @ExtTransaction
	@Transactional
	public void add() {
		// 调用接口的时候 接口失败 需要回滚，但是日志记录不需要回滚。
		// logService.addLog(); // 后面程序发生错误，不能影响到我的回滚### 正常当addLog方法执行完毕，就应该提交事务
		userDao.add("test001", 20);
		// int i = 1 / 0;
		System.out.println("################");
		userDao.add("test002", 21);

	}
	// 方法执行完毕之后，才会提交事务

	public void del() {
		// TODO Auto-generated method stub

	}

	/*
	 * public void del() { System.out.println("del"); }
	 */

}
