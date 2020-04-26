package com.yc.springboot_mvc.res_mvc_boot;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.biz.ResorderBiz;
import com.yc.config.AppConfig;
import com.yc.config.AppConfig_Swagger;
import com.yc.web.entity.CartItem;


@RunWith(SpringJUnit4ClassRunner.class)   // 请导入    spring-test包
@ContextConfiguration(classes = {AppConfig.class})    //IOC
public class TestBiz {
	
	@Autowired
	private ResorderBiz resorderBiz;
	
	@Test   //下订
	public void testOrder() throws SQLException {
		Resorder o=new Resorder();
		o.setPs("附言");
		o.setAddress("湖南");
		o.setTel("15386490869");
		o.setUserid(1);
		
		Map<Integer,CartItem> shopCart=new HashMap();
		
		Resfood r=new Resfood();
		r.setFid(1);
		
		CartItem ci=new CartItem();
		ci.setNum(1);
		ci.setFood(  r   );
		
		resorderBiz.completeOrder(o, shopCart);
	}
	
	
}
