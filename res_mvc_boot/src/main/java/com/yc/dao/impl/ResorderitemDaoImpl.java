package com.yc.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yc.bean.Resorderitem;
import com.yc.dao.ResorderitemDao;

@Repository
public class ResorderitemDaoImpl implements ResorderitemDao {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void insertResorderitem(Resorderitem item) {
		String sql="insert into resorderitem(roid,fid,dealprice,num) values( ?,?,?,?)";
		this.jdbcTemplate.update(sql, item.getRoid(), item.getFid(),item.getDealprice(), item.getNum() );
	}

}
