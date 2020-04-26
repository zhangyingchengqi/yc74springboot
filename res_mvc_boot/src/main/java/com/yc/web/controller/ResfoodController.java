package com.yc.web.controller;

import static com.yc.utils.YcConstants.RESFOODLIST;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.biz.ResfoodBiz;
import com.yc.biz.ResorderBiz;
import com.yc.biz.impl.ResorderBizImpl;
import com.yc.utils.YcConstants;
import com.yc.web.entity.CartItem;
import com.yc.web.entity.JsonModel;

@RestController   //   @Controller   @ResponseBody
public class ResfoodController {
	@Autowired
	private ResfoodBiz resfoodBiz;
	@Autowired
	private ResorderBiz resorderBiz;

	@RequestMapping(value = "confirmOrder", method = RequestMethod.POST)
	public JsonModel confirmOrder(HttpSession session, Resorder resorder, JsonModel jm) {
		if (session.getAttribute(YcConstants.LOGINUSER) == null) {
			jm.setCode(0);
			jm.setMsg("user has not been logined....");
			return jm;
		}
		// 查询用户id 从session中取出登录 用户.
		Resuser resuser = (Resuser) session.getAttribute(YcConstants.LOGINUSER);
		resorder.setUserid(resuser.getUserid());
		// 准备 Resorderitem数据
		if (session.getAttribute(YcConstants.CART) == null) {
			jm.setCode(0);
			jm.setMsg("you have not buy any thing....");
			return jm;
		}
		Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute(YcConstants.CART);

		resorderBiz.completeOrder(resorder, cart);
		session.removeAttribute(YcConstants.CART);
		jm.setCode(1);
		return jm;
	}

	@RequestMapping(value = "/findAllFoods", method = {RequestMethod.GET,RequestMethod.POST})
	public JsonModel findAllFoods(HttpSession session, JsonModel jm) {
		ServletContext application=session.getServletContext();
		List<Resfood> list = null;
		if (application.getAttribute(RESFOODLIST) != null) {
			list = (List<Resfood>) application.getAttribute(RESFOODLIST);
		} else {
			// 2. 没有，则查
			list = resfoodBiz.findAll();
			application.setAttribute(YcConstants.RESFOODLIST, list);
		}
		// 返回jsonModel
		jm.setCode(1);
		jm.setObj(list);
		return jm;
	}

	@RequestMapping("/findFood")
	public JsonModel findFood(HttpSession session,  Resfood food, JsonModel jm) {
		ServletContext application=session.getServletContext();
		List<Resfood> list = null;
		if (application.getAttribute(RESFOODLIST) != null) {
			list = (List<Resfood>) application.getAttribute(RESFOODLIST);
		} else {
			// 2. 没有，则查
			list = resfoodBiz.findAll();
			application.setAttribute(YcConstants.RESFOODLIST, list);
		}
		for (Resfood r : list) {
			if (r.getFid() == food.getFid()) {
				jm.setCode(1);
				jm.setObj(r);
				return jm;
			}
		}
		jm.setCode(0);
		return jm;
	}

}
