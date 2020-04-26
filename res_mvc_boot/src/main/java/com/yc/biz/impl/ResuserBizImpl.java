package com.yc.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.bean.Resuser;
import com.yc.biz.ResuserBiz;
import com.yc.dao.ResuserDao;

@Service
public class ResuserBizImpl implements ResuserBiz {
	@Autowired
	private ResuserDao resuserDao;

	@Override
	public Resuser login(Resuser user) {
		return resuserDao.login(user);
	}

}
