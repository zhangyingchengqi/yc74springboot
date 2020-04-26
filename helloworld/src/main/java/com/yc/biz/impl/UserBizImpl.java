package com.yc.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.bean.User;
import com.yc.biz.CreditSystemService;
import com.yc.biz.UserBiz;

@Service
public class UserBizImpl implements UserBiz {
	@Autowired
	private CreditSystemService creditSystemService;
	
	@Override
	public int getCredit(int uid) {
		return this.creditSystemService.getUserCredit(uid);
	}

	@Override
	public User addUser(String uname) {
		User u=new User();
		u.setUname(uname);
		u.setAge(20);
		return u;
	}

	@Override
	public List<User> find() {
		List<User> list=new ArrayList<>();
		for( int i=0;i<10;i++) {
			User u=new User();
			u.setUname("张"+i);
			u.setAge(20+i);
			list.add(u);
		}
		return list;
	}

	

}
