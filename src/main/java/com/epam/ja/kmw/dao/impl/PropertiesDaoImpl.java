package com.epam.ja.kmw.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.model.Properties;

public class PropertiesDaoImpl {

	private static final Logger LOGGER = LogManager.getLogger(ConnectionDao.class);
	ConnectionDao connectionDao;
	CreateTablePropertiesDelegation createTablePropertiesDelegation = new CreateTablePropertiesDelegation();

	/**
	 * Creates PropertiesDaoIml object and calls methodCreateTableProperties.
	 * 
	 * @param connectionDao
	 *            initialize created object.
	 */
	public PropertiesDaoImpl(ConnectionDao connectionDao) {
		this.connectionDao = connectionDao;
		methodCreateTableProperties();
	}

	/**
	 * Calls private method createTableProperties.
	 * 
	 * @return true if operation succeed, false if not.
	 */
	public boolean methodCreateTableProperties() {
		return createTableProperties();
	}

	/**
	 * Calls method createTablePropertiesDelegation and returns true. If called
	 * method throws SQLException, this exception is caught and false is
	 * returned.
	 * 
	 * @return true if operation succeed, false if not.
	 */
	private boolean createTableProperties() {

		try {

			createTablePropertiesDelegation.createTablePropertiesDelegation();
			return true;

		} catch (SQLException e) {
			LOGGER.error("Fail to create a table 'Properties' in database. :" + e.getMessage());
			return false;
		}
	}

	class CreateTablePropertiesDelegation {

		/**
		 * Creates table named Properties and inserts in its columns initial
		 * values.
		 * 
		 * @throws SQLException
		 *             when connection with a database fails
		 */
		public void createTablePropertiesDelegation() throws SQLException {
			String createPropertiesTableQuery = "CREATE TABLE Properties (id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "lastDate varchar(255), runCounter INTEGER)";
			String addDefaultQuery = "INSERT INTO Properties VALUES(NULL,?, ?)";
			connectionDao.getStatement().execute(createPropertiesTableQuery);
			LOGGER.info("Created Properties database");

			PreparedStatement preparedStatement = connectionDao.getConnection().prepareStatement(addDefaultQuery);
			preparedStatement.setString(1, "");
			preparedStatement.setInt(2, 0);
			preparedStatement.executeUpdate();
			preparedStatement.close();

		}

	}

	/**
	 * Returned object is an instance of a Properties class and is selected from
	 * a table named Properties in a database. If statement execution fails
	 * method catches SQLException and returns null.
	 * 
	 * @return properties
	 */
	public Properties getProperties() {
		String getPropertiesQuery = "SELECT * FROM Properties";

		LOGGER.info("Getting properties from base...");

		try (ResultSet result = connectionDao.getStatement().executeQuery(getPropertiesQuery)) {

			result.next();
			String lastDate = result.getString(2);
			int runCounter = result.getInt(3);

			Properties properties = new Properties(lastDate, runCounter);

			LOGGER.info("Successfully collected properties from the database.");

			return properties;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Fail to collect properties from database.");
			return null;
		}
	}

	/**
	 * Updates properties in a database and returns true. If connection fails
	 * method catches SQLException and returns false.
	 * 
	 * @param properties
	 *            object that will be updated.
	 * @return true if operation succeed, false if not.
	 */
	public boolean updateProperties(Properties properties) {
		String updatePropertiesQuery = "UPDATE Properties SET lastDate = ?, runCounter = ?  WHERE id = ?";

		LOGGER.info("Updating properties in database...");

		try (PreparedStatement prepareStatement = connectionDao.getConnection()
				.prepareStatement(updatePropertiesQuery)) {

			prepareStatement.setString(1, properties.getLastDate());
			prepareStatement.setInt(2, properties.getRunCounter());
			prepareStatement.setInt(3, 1);
			prepareStatement.executeUpdate();
			LOGGER.info("Successfully updated properties in database.");
			return true;
		} catch (SQLException e) {
			LOGGER.error(
					"Fail when updating properties in databse. Caused by: \n" + Arrays.toString(e.getStackTrace()));
			return false;
		}
	}

}