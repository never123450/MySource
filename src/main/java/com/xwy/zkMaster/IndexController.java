package com.xwy.zkMaster;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {
	@Value("${server.port}")
	private String serverPort;

	@RequestMapping("/getServerInfo")
	public String getServerInfo() {
		return "serverPort:" + serverPort + (ElectionMaster.isSurvival ? "选举为主服务器" : "该服务器为从服务器");
	}

	public static void main(String[] args) {
		// 1.项目启动的时候会在zk上创建一个相同的临时节点
		// 2.谁能够创建成功谁就是为主服务器
		// 3.使用服务监听节点是否被删除，如果接受到节点被删除的话，重新开始选举（重新开始创建节点）

	}

}
