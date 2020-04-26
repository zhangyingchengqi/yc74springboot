package com.yc.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.yc.bean.Resuser;
import com.yc.dao.ResuserDao;

@Repository
public class ResuserDaoImpl implements ResuserDao {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Resuser login(Resuser user) {
		String sql="select * from resuser where username=? and pwd=?";
		return this.jdbcTemplate.queryForObject(
		        "select * from resuser where username=? and pwd=?",
		        (resultSet, rowNum) -> {
		        	Resuser a = new Resuser();
		        	a.setUserid(    resultSet.getInt(1)  );
		        	a.setUsername(   resultSet.getString(2));
		        	a.setPwd(   resultSet.getString(3));
		        	a.setEmail(resultSet.getString(4));
		            return a;
		        },
		        user.getUsername(),  user.getPwd() );
	}

}
