package com.yc.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yc.bean.Resfood;
import com.yc.bean.Resuser;
import com.yc.dao.ResfoodDao;

@Repository
public class ResfoodDaoImpl implements ResfoodDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Resfood> getAllFood() {
		return this.jdbcTemplate.query(
		        "select * from resfood",
		        (resultSet, rowNum) -> {
		        	Resfood a = new Resfood();
					a.setFid(resultSet.getInt(1));
					a.setFname(resultSet.getString(2));
					a.setNormprice(resultSet.getDouble("normprice"));
					a.setRealprice(resultSet.getDouble("realprice"));
					a.setDetail(resultSet.getString("detail"));
					a.setFphoto(resultSet.getString("fphoto"));
					return a;
		        });
	}

	@Override
	public Resfood findResfood(int fid) {
		return this.jdbcTemplate.queryForObject("select * from resfood where fid=?", (resultSet, rowNum) -> {
			Resfood a = new Resfood();
			a.setFid(resultSet.getInt(1));
			a.setFname(resultSet.getString(2));
			a.setNormprice(resultSet.getDouble("normprice"));
			a.setRealprice(resultSet.getDouble("realprice"));
			a.setDetail(resultSet.getString("detail"));
			a.setFphoto(resultSet.getString("fphoto"));
			return a;
		}, fid);
	}

}
