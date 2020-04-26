package com.yc.biz.impl;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;
import com.yc.biz.ResorderBiz;
import com.yc.dao.ResorderDao;
import com.yc.dao.ResorderitemDao;
import com.yc.web.entity.CartItem;

@Service
@Transactional
public class ResorderBizImpl implements ResorderBiz {
	
	@Autowired
	private ResorderDao resorderDao;
	@Autowired
	private ResorderitemDao resorderitemDao;

	@Override
	public void completeOrder(Resorder resorder, Map<Integer, CartItem> shopCart)  {
		Resorder orderResult=resorderDao.insertResorder(resorder);
		if( shopCart!=null&& shopCart.size()>0){
			for(   Map.Entry<Integer, CartItem> entry: shopCart.entrySet()){
				   Resorderitem ri=new Resorderitem();
				   ri.setRoid(orderResult.getRoid());   //订单编号
				   ri.setNum(entry.getValue().getNum());
				   ri.setFid(    entry.getKey() );
				   ri.setDealprice(  entry.getValue().getFood().getRealprice() );
				   resorderitemDao.insertResorderitem(ri);
			}
		}
	}

}
