package com.epam.ja.kmw.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author filipm Provides functions needed to control connection between
 *         application and database.
 */
public class ConnectionDao implements AutoCloseable {

	private static final String DB_DRIVER = "org.sqlite.JDBC";

	private static final String DB_URL = "jdbc:sqlite:books.db";

	private static final Logger LOGGER = LogManager.getLogger(ConnectionDao.class);
	CreateConnectionDelegate createConnectionDelegate = new CreateConnectionDelegate();
	protected Connection connection;
	protected Statement statement;

	/**
	 * Creates ConnectionDao object and calls methodCreateConnection. If
	 * creation fails method catches ClassNotFoundException.
	 */
	public ConnectionDao() {
		try {
			Class.forName(ConnectionDao.DB_DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage());
		}
		methodCreateConnection();
	}

	/**
	 * Calls private method createConnection.
	 * 
	 * @return true if operation succeed, false if not.
	 */
	public boolean methodCreateConnection() {
		return createConnection();
	}

	/**
	 * Calls method createConnectionDelegate and returns true. If called method
	 * throws SQLException, this exception is caught and false is returned.
	 * 
	 * @return true if operation succeed, false if not.
	 */
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
		/**
		 * Creates connection with a database.
		 * 
		 * @throws SQLException
		 *             when connection creation with a database fails.
		 */
		public void createConnectionDelegate() throws SQLException {
			connection = DriverManager.getConnection(DB_URL);
			statement = connection.createStatement();
		}
	}

	/**
	 * Tries to close connection with a database. If it fails, method catches
	 * SQLException.
	 */
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

	/**
	 * Returned object is an instance of a Connection class.
	 * 
	 * @return connection
	 * @throws SQLException
	 *             when connection is not available.
	 */
	public Connection getConnection() throws SQLException {
		if (connection != null)
			return connection;
		else
			throw new SQLException("Connection is not available");
	}

	/**
	 * Returned object is an instance of a Statement class.
	 * 
	 * @return statement
	 * @throws SQLException
	 *             when statement is not available (no connection).
	 */
	public Statement getStatement() throws SQLException {
		if (connection != null)
			return statement;
		else
			throw new SQLException("Statement is not available");
	}

	@Override
	public void close() {
		closeConnection();

	}
}