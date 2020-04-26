package com.yc;

import java.sql.SQLException;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.yc.bean.Account;
import com.yc.biz.AccountBiz;
import com.yc.config.AppConfig;
import com.yc.dao.AccountDao;
import com.yc.dao.InAccountDao;

@RunWith(SpringJUnit4ClassRunner.class)   // 请导入    spring-test包
@ContextConfiguration(classes = {AppConfig.class})    //IOC
public class TestBiz {
	
	@Autowired
	private AccountBiz accountBiz;
	
	@Test   //查询
	public void testFind() throws SQLException {
		Account a=accountBiz.find("2");
		System.out.println(  a );    
	}
	
	@Test   //存款
	public void testDeposit() throws SQLException {
		Account a=accountBiz.deposite("2", 10);
		System.out.println(  a );    
	}
	
	@Test   //这是一个测试用例
	public void testWithdraw() throws SQLException {
		Account a=accountBiz.withdraw("2", 100);
		System.out.println(  a );    //  Account   2  98        InAccount  
	}
	
	@Test   //这是一个测试用例
	public void testTransfer() throws SQLException {
		Account a=accountBiz.transfer("2", 100, "1");
		System.out.println(  a );    //  Account   2  98        InAccount    
	}
	
}
