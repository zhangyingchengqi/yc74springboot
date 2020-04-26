package com.yc.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBiz;
import com.yc.dao.ResfoodDao;

@Service
public class ResfoodBizImpl implements ResfoodBiz {
	@Autowired
	private ResfoodDao resfoodDao;

	@Override
	public List<Resfood> findAll() {
		return resfoodDao.getAllFood();
	}

}
