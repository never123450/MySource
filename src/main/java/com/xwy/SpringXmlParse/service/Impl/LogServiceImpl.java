package com.xwy.SpringXmlParse.service.Impl;

import com.xwy.SpringXmlParse.dao.LogDao;
import com.xwy.SpringXmlParse.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LogServiceImpl implements LogService {
	@Autowired
	private LogDao logDao;

	@Transactional(propagation = Propagation.NEVER)
	public void addLog() {
		logDao.add("addLog" + System.currentTimeMillis());
		// int i = 1 / 0;
	}

}
