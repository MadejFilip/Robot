package com.epam.ja.kmw.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.PropertiesDao;
import com.epam.ja.kmw.model.Properties;

public class PropertiesDaoImpl implements PropertiesDao {

	private static final Logger LOGGER = LogManager.getLogger(ConnectionDao.class);
	ConnectionDao connectionDao;

	public PropertiesDaoImpl(ConnectionDao connectionDao) {
		this.connectionDao=connectionDao;
		createTableProperties();
	}
	

	
	private void createTableProperties() {
		String createPropertiesTableQuery = "CREATE TABLE Properties (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "lastDate varchar(255), runCounter INTEGER)";
		String addDefaultQuery = "INSERT INTO Properties VALUES(NULL,?, ?)";
		try {

			connectionDao.getStatement().execute(createPropertiesTableQuery);
			LOGGER.info("Created Properties database");
			

			PreparedStatement preparedStatement = connectionDao.getConnection().prepareStatement(addDefaultQuery);
			preparedStatement.setString(1, "");
			preparedStatement.setInt(2, 0);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			LOGGER.error("Fail to create a table 'Properties' in database. :" + e.getMessage());
		}
	}
	@Override
	public Properties getProperties() {
		String getPropertiesQuery = "SELECT * FROM Properties";

		LOGGER.info("Getting properties from base...");

		try {

			ResultSet result = connectionDao.getStatement().executeQuery(getPropertiesQuery);
			result.next();
			String lastDate = result.getString(2);
			int runCounter = result.getInt(3);

			Properties properties = new Properties(lastDate, runCounter);

			LOGGER.info("Successfully collected properties from the database.");
			result.close();

			return properties;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Fail to collect properties from database.");
			return null;
		}
	}

	@Override
	public void updateProperties(Properties properties) {
		String updatePropertiesQuery = "UPDATE Properties SET lastDate = ?, runCounter = ?  WHERE id = ?";

		LOGGER.info("Updating properties in database...");

		try {
			PreparedStatement prepareStatement = connectionDao.getConnection().prepareStatement(updatePropertiesQuery);
			prepareStatement.setString(1, properties.getLastDate());
			prepareStatement.setInt(2, properties.getRunCounter());
			prepareStatement.setInt(3, 1);
			prepareStatement.executeUpdate();
			LOGGER.info("Successfully updated properties in database.");
		} catch (SQLException e) {
			LOGGER.error("Fail when updating properties in databse. Caused by: \n" + e.getStackTrace());
		}

	}

}
