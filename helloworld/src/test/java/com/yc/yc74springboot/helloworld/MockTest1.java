package com.yc.yc74springboot.helloworld;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.yc.biz.CreditSystemService;
import com.yc.biz.UserBiz;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MockTest1 {
	@Autowired
	private UserBiz userService;
	@MockBean   //这里我们假设  无法调用 creditSystemService  (即无法  @Service ,因为它可能是另一个系统中的实现对象),这里用MockBean来创建它,
	//这个MockBean注解可以自动注入spring管理的 service,用来提供一个模式实现，这个实现是一个代理实现
	private CreditSystemService creditSystemService;
	@Test
	public void testService() {
		System.out.println(    this.creditSystemService );   //动态代理
		int uid=1;
		given( this.creditSystemService.getUserCredit(anyInt()) ).willReturn(  100 );   //表示无论给  getUserCredit()传入什么参数，都返回  100;
		int credit=userService.getCredit(uid);
		assertEquals(   100,uid  );
		
		
	}
}
