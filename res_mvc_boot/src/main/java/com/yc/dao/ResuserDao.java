package com.yc.dao;


import com.yc.bean.Resuser;

public interface ResuserDao {
	//使用@Select注解指明getById方法要执行的SQL
		//"select * from resuser where username=#{username} and pwd=#{pwd}"
		public Resuser login(Resuser user);
}
