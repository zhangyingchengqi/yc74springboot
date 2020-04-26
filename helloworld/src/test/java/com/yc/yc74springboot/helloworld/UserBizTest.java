package com.yc.yc74springboot.helloworld;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.yc.bean.User;
import com.yc.biz.UserBiz;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBizTest {
	@Autowired
	private UserBiz userBiz;
	
	@Test
	public void testAdd() {
		User u=userBiz.addUser("张三");
		assertEquals("张三", u.getUname());
	}
}
