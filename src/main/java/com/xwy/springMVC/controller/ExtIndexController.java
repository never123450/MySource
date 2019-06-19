package com.xwy.springMVC.controller;


import com.xwy.springMVC.extannotation.ExtController;
import com.xwy.springMVC.extannotation.ExtRequestMapping;

@ExtController
@ExtRequestMapping("/ext")
public class ExtIndexController {
	//ext/test/?name=122&age=6440654
	@ExtRequestMapping("/test")
	public String test(String name,Integer age) {
		System.out.println("手写springmvc框架...");
		return "index";
	}

}
