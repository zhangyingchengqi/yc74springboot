package com.yc.web.controller;

import static com.yc.utils.YcConstants.RESFOODLIST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBiz;
import com.yc.utils.YcConstants;
import com.yc.web.entity.CartItem;
import com.yc.web.entity.JsonModel;

@RestController
public class ResorderController {
	
	@Autowired
	private ResfoodBiz resfoodBiz;
	
	@RequestMapping(value="/getCartInfo",method= {RequestMethod.GET,RequestMethod.POST} )
	public JsonModel getCartInfoOp(HttpServletRequest req, HttpSession session, JsonModel jm) throws IOException {
		List<CartItem> list=new ArrayList<CartItem>();
		if (session.getAttribute(YcConstants.CART) != null) {
			jm.setCode(1);
			//变更，将对象改为list，方便后面循环取值
			Map<Integer, CartItem> cart = null;
			cart = (Map<Integer, CartItem>) session.getAttribute(YcConstants.CART);
			Set<Integer> sets=cart.keySet();
			Iterator<Integer> iterator=sets.iterator();
			while(iterator.hasNext()){
				int x=iterator.next();
				list.add(  cart.get(x)  );
			}
		} else {
			jm.setCode(0);
		}
		jm.setObj(list);
		return jm;
	}
	
	
	@RequestMapping(value="/delorder",method=RequestMethod.GET)
	public JsonModel delorder(HttpSession session,JsonModel jm, Resfood food)  {
		int fid = food.getFid();
		// 购物车跟用户相关，所以存 session
		Map<Integer, CartItem> cart = null;
		if (session.getAttribute(YcConstants.CART) != null) {
			cart = (Map<Integer, CartItem>) session.getAttribute(YcConstants.CART);
		} else {
			cart = new HashMap<Integer, CartItem>();
		}
		if(  cart.containsKey(fid)){
			cart.remove(fid);
			jm.setCode(1);
		}else{
			jm.setCode(0);
		}
		// 将cart存到 session中
		session.setAttribute(YcConstants.CART, cart);
		return jm;
	}

	@RequestMapping(value="/clearAll",method= {RequestMethod.GET,RequestMethod.POST})
	public JsonModel clearAllOp(HttpSession session,JsonModel jm)  {
		session.removeAttribute(YcConstants.CART);
		jm.setCode(1);
		return jm;
	}
	
	@RequestMapping(value="/orderJson",method= {RequestMethod.GET,RequestMethod.POST} )
	public JsonModel orderJsonOp(HttpServletRequest req,JsonModel jm,HttpSession session)  {
		commonOrder(req,session);
		jm.setCode(1);
		return jm;
	}
	
	private void commonOrder(HttpServletRequest req,HttpSession session ) {
		ServletContext application=session.getServletContext();
		int fid = Integer.parseInt(req.getParameter("fid"));
		int num = Integer.parseInt(req.getParameter("num"));
		List<Resfood> list = null;
		if (application.getAttribute(RESFOODLIST) != null) {
			list = (List<Resfood>) application.getAttribute(RESFOODLIST);
		} else {
			// 2. 没有，则查
			list = resfoodBiz.findAll();
			application.setAttribute(YcConstants.RESFOODLIST, list);
		}
		Resfood food=null;
		for (Resfood r : list) {
			if (r.getFid() == fid) {
				food=r;
				break;
			}
		}
		// 购物车跟用户相关，所以存 session
		Map<Integer, CartItem> cart = null;
		if (session.getAttribute(YcConstants.CART) != null) {
			cart = (Map<Integer, CartItem>) session.getAttribute(YcConstants.CART);
		} else {
			cart = new HashMap<Integer, CartItem>();
		}
		// 看这个购物车是否有 fid
		CartItem ci = null;
		if (cart.containsKey(fid)) {
			// 证明用户已经购买了这个菜，则数量增加
			ci = cart.get(fid);
			int newnum = ci.getNum() + num;
			ci.setNum(newnum);
		} else {
			// 还没有买过, 则创建 一个cartItem存到map中
			ci = new CartItem();
			ci.setFood(food);
			ci.setNum(num);
		}
		if (ci.getNum() <= 0) {
			cart.remove(fid);
		} else {
			ci.getSmallCount(); // 计算小计.
			// 将cartitem存到map中
			cart.put(fid, ci);
		}
		// 将cart存到 session中
		session.setAttribute(YcConstants.CART, cart);
	}
}
