package com.yc.biz;

import java.util.List;

import com.yc.bean.User;

public interface UserBiz {
	
	public int getCredit(  int uid );
	
	public User addUser(String uname);
	
	public List<User> find();
}
