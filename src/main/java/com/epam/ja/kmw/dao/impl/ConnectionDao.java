package com.epam.ja.kmw.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionDao implements AutoCloseable {

	private static final String DB_DRIVER = "org.sqlite.JDBC";

	private static final String DB_URL = "jdbc:sqlite:books.db";

	private static final Logger LOGGER = LogManager.getLogger(ConnectionDao.class);
	CreateConnectionDelegate createConnectionDelegate = new CreateConnectionDelegate();
	protected Connection connection;
	protected Statement statement;

	public ConnectionDao() {
		try {
			Class.forName(ConnectionDao.DB_DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage());
		}
		methodCreateConnection();
	}

	public boolean methodCreateConnection() {
		return createConnection();
	}

	private boolean createConnection() {

		LOGGER.info("Connecting to database...");
		try {
			createConnectionDelegate.createConnectionDelegate();
			LOGGER.info("Successfully connected with database.");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.info("Fail to cennect with database.");
			return false;
		}
	}

	class CreateConnectionDelegate {
		public void createConnectionDelegate() throws SQLException {
			connection = DriverManager.getConnection(DB_URL);
			statement = connection.createStatement();
		}
	}

	private void closeConnection() {

		LOGGER.info("Closing connection with database...");

		try {
			statement.close();
			connection.close();

			LOGGER.info("Successfully ended connection with database.");
		} catch (SQLException e) {
			LOGGER.error("Fail to end connection.");
			e.printStackTrace();
		}

	}

	public Connection getConnection() throws SQLException {
		if (connection != null)
			return connection;
		else
			throw new SQLException("Connection is not available");
	}

	public Statement getStatement() throws SQLException {
		if (connection != null)
			return statement;
		else
			throw new SQLException("Statement is not available");
	}

	@Override
	public void close() throws SQLException {
		closeConnection();

	}
}