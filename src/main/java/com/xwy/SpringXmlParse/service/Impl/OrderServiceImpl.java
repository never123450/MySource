package com.xwy.SpringXmlParse.service.Impl;

import com.xwy.SpringXmlParse.annotation.ExtService;
import com.xwy.SpringXmlParse.service.OrderService;

@ExtService
public class OrderServiceImpl implements OrderService {

	public void addOrder() {
		System.out.println("addOrder");
	}

}
