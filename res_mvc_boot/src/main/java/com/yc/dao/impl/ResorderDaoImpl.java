package com.yc.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.yc.bean.Resorder;
import com.yc.dao.ResorderDao;

@Repository
public class ResorderDaoImpl implements ResorderDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Resorder insertResorder(Resorder resorder) {
		String sql = "insert into resorder( userid,address,tel,ordertime,deliverytime,ps,status) values( ?,?,?,now(),now(),?,0)";
		// 自增列的问题 roid订单号.
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(connection -> {
			// You need to specify Statement.RETURN_GENERATED_KEYS to
			// Statement.executeUpdate(), Statement.executeLargeUpdate() or
			// Connection.prepareStatement().
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, resorder.getUserid() + "");
			ps.setString(2, resorder.getAddress());
			ps.setString(3, resorder.getTel());

			ps.setString(4, resorder.getPs());
			return ps;
		}, keyHolder);
		resorder.setRoid(keyHolder.getKey().intValue());
		return resorder;
	}

}
