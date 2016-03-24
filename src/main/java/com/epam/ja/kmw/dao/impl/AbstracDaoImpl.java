package com.epam.ja.kmw.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbstracDaoImpl {

	private static final String DB_DRIVER = "org.sqlite.JDBC";
	private static final String DB_URL = "jdbc:sqlite:books.db";

	private static final Logger LOGGER = LogManager.getLogger(AbstracDaoImpl.class);

	protected Connection connection;
	protected Statement statement;

	public AbstracDaoImpl() {
		try {
			Class.forName(AbstracDaoImpl.DB_DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void createConnection() {

		LOGGER.info("Connecting to database...");

		try {
			connection = DriverManager.getConnection(DB_URL);
			statement = connection.createStatement();
			LOGGER.info("Successfully connected with database.");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.info("Fail to cennect with database.");
		}
	}

	public void closeConnection() {

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
}
