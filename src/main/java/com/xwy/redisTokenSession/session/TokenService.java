
package com.xwy.redisTokenSession.session;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;


@Service
public class TokenService {
	@Autowired
	private RedisService redisService;

	// 1. 使用token方式替代Session功能
	// 存入
	public String put(String value) {
		// 1.判断是否为空
		if (value == null) {
			return null;
		}
		// 2.先生成对应的token (token 实际上等于 key)
		String token = getToken();
		// 3.存入在redis中
		redisService.setString(token, value);
		// 4.直接返回对应的token
		return token;
	}

	public String get(String token) {
		return redisService.getString(token);
	}

	public String getToken() {
		return UUID.randomUUID().toString();
	}

}
