package com.mire.sixclass;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

public class DBUtility {
	// ����Լ��� ����
	// ����Լ� : �����ͺ��̽� ���� ��û
	// Connection : �����ͺ��̽� �������ִ� �ڵ鰪
	public static Connection getConnection() {
		Connection con = null;
		FileReader fr = null;

		try {
			fr = new FileReader("src\\com\\mire\\sixclass\\db.properties");
			Properties properties = new Properties();

			properties.load(fr);

			String DRIVER = properties.getProperty("DRIVER");
			String URL = properties.getProperty("URL");
			String userID = properties.getProperty("userID");
			String userPassword = properties.getProperty("userPassword");

			// ����̹� ����
			Class.forName(DRIVER);
			// ����Ÿ���̽� ����
			con = (Connection) DriverManager.getConnection(URL, userID, userPassword);
		} catch (ClassNotFoundException e) {
			System.out.println("mysql database connection fail");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("mysql database connection fail");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("file not found db.properties");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("file db.properties connection fail");
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return con;
	}
}