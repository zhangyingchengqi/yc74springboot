package com.yc.biz;

import java.sql.SQLException;
import java.util.Map;

import com.yc.bean.Resorder;
import com.yc.web.entity.CartItem;

public interface ResorderBiz {
	
	/**
	 * 
	 * @param resorder: 订单信息
	 * @param shopCart:  购物车，对应订单项
	 */
	public void  completeOrder(  Resorder resorder, Map<Integer, CartItem> shopCart  ) ;
}
