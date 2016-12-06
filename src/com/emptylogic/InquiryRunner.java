package com.emptylogic;

import java.sql.Connection;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class InquiryRunner implements Runnable {

	@Resource(name="jdbc/yourdb")
	private DataSource datasource;
	
	@Override
	public void run() {
		try {
			Connection connection = datasource.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}