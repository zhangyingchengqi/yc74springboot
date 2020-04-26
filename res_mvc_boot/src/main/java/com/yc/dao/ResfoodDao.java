package com.yc.dao;

import java.util.List;

import com.yc.bean.Resfood;

public interface ResfoodDao {
	
	//"select * from resfood"
	public List<Resfood> getAllFood();
	
	public Resfood findResfood(  int fid  );
	
	
	
}