package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

//@Component
@Resource
public class JdbcDaoImpl {

//	@Resource(name="dataSource")
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	NamedParameterJdbcTemplate namedParameterjdbcTemplate;

	public DataSource getDataSource() {
		return dataSource;
	}
	@Autowired
	public void setDataSource(DataSource dataSource) {
//		 this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterjdbcTemplate= new NamedParameterJdbcTemplate(dataSource);
		// jdbcTemplate.setDataSource(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	
	public Circle getCircle(int circleId) {
		Circle circle = null;
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement("select * from Circle where id=?");
			ps.setInt(1, circleId);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				circle = new Circle(resultSet.getString("name"), circleId);
			}
			resultSet.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return circle;
	}

	public String getCircleName(int id) {
		String s = "select name  from circle where id=?";
		return getJdbcTemplate().queryForObject(s, new Object[] { id }, String.class);
	}

	  public int circleCount()
	  {
		  String s = "select count(*) from circle";
//		  jdbcTemplate.setDataSource(dataSource);
		  return jdbcTemplate.queryForObject(s, Integer.class);
	  }
	  
	 
	  public Circle getCircleForId(int id)
	  {
		  String s = "select * from Circle where id = ?";
		  return jdbcTemplate.queryForObject(s, new Object[]{id}, new CircleMapper());
	  }
	  
	  public List<Circle> getAllCircle()
	  {
		  String s = "select * from Circle";
		  return jdbcTemplate.query(s, new CircleMapper());
	  }
	  
	 /* public void updateTable(Circle  circle)
	  {
		  String s = "insert into Circle(id,name) values (?,?)";
			jdbcTemplate.update(s, new Object[]{circle.getId(),circle.getName()});	  
	  }*/
	  
	  
	  public void updateTable(Circle  circle)
	  {
		  String s = "insert into Circle(id,name) values (:id,:name)";
          SqlParameterSource parameterSource =new  MapSqlParameterSource("id", circle.getId()).addValue("name", circle.getName());		  
          namedParameterjdbcTemplate.update(s, parameterSource);
	  }
	  
	  
	  public void createTable()
	  {
		  String s = "create Table Triangle (id integer,name varchar(50))";
		  jdbcTemplate.execute(s);
	  }
	  
	  
	  private static final class CircleMapper implements RowMapper<Circle>
	  {

		@Override
		public Circle mapRow(ResultSet resultSet, int arg1) throws SQLException {
			
			Circle circle = new Circle();
			circle.setName(resultSet.getString("name"));
			circle.setId(resultSet.getInt("id"));
			return circle;
		}

		
		  
	  }
	
	/*
	 * public Circle getCircle(int circleId) { Connection connection = null;
	 * Circle circle = null; String driver =
	 * "org.apache.derby.jdbc.ClientDriver"; try {
	 * Class.forName(driver).newInstance(); connection =
	 * DriverManager.getConnection("jdbc:derby://localhost:1527/db");
	 * PreparedStatement ps =
	 * connection.prepareStatement("select * from Circle where id=?");
	 * ps.setInt(1, circleId);
	 * 
	 * 
	 * ResultSet resultSet = ps.executeQuery(); if(resultSet.next()) { circle=
	 * new Circle(resultSet.getString("name"), circleId); } resultSet.close();
	 * ps.close(); } catch (Exception e) { e.printStackTrace(); } finally { try
	 * { connection.close(); } catch (SQLException e) { e.printStackTrace(); } }
	 * 
	 * return circle; }
	 */

}
