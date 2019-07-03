package com.xwy.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转页面<br>
 * 
 */
@Controller
public class UserController {
  
	@RequestMapping("/pageIndex")
	public String pageIndex() {
		return "pageIndex";
	}

}
