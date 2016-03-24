package com.epam.ja.kmw.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.PropertiesDao;
import com.epam.ja.kmw.model.Properties;

public class PropertiesDaoImpl extends AbstracDaoImpl implements PropertiesDao {

	private static final Logger LOGGER = LogManager.getLogger(AbstracDaoImpl.class);

	public void createTable() {
		String createPropertiesTableQuery = "CREATE TABLE IF NOT EXISTS Properties (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "lastDate varchar(255), runCounter INTEGER)";
		try {
			boolean execute = statement.execute(createPropertiesTableQuery);
			if (!execute) {
				LOGGER.info("Can't find table 'Properties' in database...");
				LOGGER.info("Creating new table.");
				LOGGER.info("Successfully created table 'Properties' in database.");
			} else {
				LOGGER.info("Successfully connected with table 'Properties' in database.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Fail to find or create a table 'Properties' in database.");
		} finally {

		}
	}

	@Override
	public Properties getProperties() {
		String getPropertiesQuery = "SELECT * FROM Properties";

		LOGGER.info("Getting properties from base...");

		try {

			ResultSet result = statement.executeQuery(getPropertiesQuery);
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
			PreparedStatement prepareStatement = connection.prepareStatement(updatePropertiesQuery);
			prepareStatement.setString(1, properties.getLastDate());
			prepareStatement.setInt(2, properties.getRunCounter());
			prepareStatement.executeUpdate();
			LOGGER.info("Successfully updated properties in database.");
		} catch (SQLException e) {
			LOGGER.error("Fail when updating properties in databse. Caused by: \n" + e.getStackTrace());
		}

	}

}
