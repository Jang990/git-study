package com.example.demo.chapter08;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;

import com.zaxxer.hikari.HikariDataSource;

public class DbQuery {
	@Autowired
	private HikariDataSource dataSource;
	
	public int count() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection(); // 풀에서 구함
			try(Statement stmt = conn.createStatement(); 
					ResultSet rs = stmt.executeQuery("Select count(*) From Member")) {
				rs.next();
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if(conn != null) 
				try { conn.close(); /*풀에 반환*/ } 
				catch (SQLException e2) { }
		}
	}
}
