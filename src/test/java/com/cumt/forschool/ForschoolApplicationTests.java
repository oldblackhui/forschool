package com.cumt.forschool;

import com.cumt.forschool.entity.MyUserDetail;
import com.cumt.forschool.entity.RoleInfo;
import com.cumt.forschool.service.RoleInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
class ForschoolApplicationTests {

	@Autowired
	RoleInfoService roleInfoService;

	@Autowired
	RedisTemplate redisTemplate;

	@Test
	void contextLoads() {
		List<RoleInfo> roleInfos = roleInfoService.listRoleByUsername("08192862");
		for (RoleInfo roleInfo : roleInfos) {
			System.out.println(roleInfo);
		}


	}

}
