package com.icp2152.dvthdh;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.IOException;
import java.util.Properties;


/**
 * Database connection.
 *
 * @author DVT HDH
 */
public class Database implements AutoCloseable
{
	private Connection connection;


	/**
	 * Creates connection to database. This connection have to be closed by caller!
	 *
	 * @throws IOException if an error occurred when reading properties file
	 * @throws ClassNotFoundException if the driver class can not be located
	 * @throws SQLException if there is a problem with SQL driver
	 */
	public Database() throws IOException, ClassNotFoundException, SQLException
	{
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/database.properties"));

		String driver = properties.getProperty("mysql.driver"),
			url = properties.getProperty("mysql.url"),
			user = properties.getProperty("mysql.user"),
			password = properties.getProperty("mysql.password");

		if (driver != null) {
			Class.forName(driver);
		}

		connection = DriverManager.getConnection(url, user != null ? user : "", password != null ? password : "");
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws Exception
	{
		if (!connection.isClosed()) {
			connection.close();
		}
	}
}
