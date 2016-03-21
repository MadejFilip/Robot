package com.epam.ja.kmw.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.BookStoreDao;
import com.epam.ja.kmw.model.BookStore;

public class BookStoreDaoImpl implements BookStoreDao {

	private static final Logger LOGGER = LogManager.getLogger(BookDaoImpl.class);

	private static final String DB_DRIVER = "org.sqlite.JDBC";
	private static final String DB_URL = "jdbc:sqlite:books.db";

	private Connection connection;
	private Statement statement;

	public BookStoreDaoImpl() {
		try {
			Class.forName(BookStoreDaoImpl.DB_DRIVER);
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

	public void createTable() {

		String createBooksTableQuery = "CREATE TABLE IF NOT EXISTS BookStores (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name varchar(255), url varchar(255), tag varchar(255), container varchar(255), nameTag varchar(255), "
				+ "priceTag varchar(255), nextTag varchar(255), add_date datetime default current_datetime)";
		try {
			boolean execute = statement.execute(createBooksTableQuery);
			if (execute) {
				LOGGER.info("Can't find table 'BookStores' in database...");
				LOGGER.info("Creating new table.");
				LOGGER.info("Successfully created table 'BookStores' in database.");
			} else {
				LOGGER.info("Successfully connected with table 'BookStores' in database.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Fail to find or create a table 'BookStores' in database.");
		} finally {

		}
	}

	@Override
	public boolean addBookStore(BookStore bookStore) {

		String addBoookStoreQuery = "INSERT INTO BookStores VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";

		LOGGER.info("Adding bookstore to database...");

		java.util.Date date = new java.util.Date();
		Date sqlDate = new Date(date.getTime());

		try {

			PreparedStatement prepareStatement = connection.prepareStatement(addBoookStoreQuery);

			prepareStatement.setString(1, bookStore.getName());
			prepareStatement.setString(2, bookStore.getUrl());
			prepareStatement.setString(3, bookStore.getTag());
			prepareStatement.setString(4, bookStore.getContainer());
			prepareStatement.setString(5, bookStore.getNameTag());
			prepareStatement.setString(6, bookStore.getPriceTag());
			prepareStatement.setString(7, bookStore.getNextTag());
			prepareStatement.setDate(8, sqlDate);

			prepareStatement.executeUpdate();

			LOGGER.info("Successfully add bookstore to database.");
		} catch (SQLException e) {
			LOGGER.error("Fail when adding bookstore to database caused by: " + e.getStackTrace());
		}

		return false;
	}

	@Override
	public boolean updateBookStore(BookStore bookStore) {
		String updateBookStoreQuery = "UPDATE BookStores SET name = ?, url = ?, tag = ?, container = ?, nameTag = ?, "
				+ "priceTag = ?, nextTag = ?  WHERE id = ?";

		LOGGER.info("Updating book in database...");

		try {
			PreparedStatement prepareStatement = connection.prepareStatement(updateBookStoreQuery);
			prepareStatement.setString(1, bookStore.getName());
			prepareStatement.setString(2, bookStore.getUrl());
			prepareStatement.setString(3, bookStore.getTag());
			prepareStatement.setString(4, bookStore.getContainer());
			prepareStatement.setString(5, bookStore.getNameTag());
			prepareStatement.setString(6, bookStore.getPriceTag());
			prepareStatement.setString(7, bookStore.getNextTag());
			prepareStatement.setInt(7, bookStore.getId());
			prepareStatement.executeUpdate();
			LOGGER.info("Successfully updated bookstore in database.");
			return true;
		} catch (SQLException e) {
			LOGGER.error("Fail when updating bookstore in databse. Caused by: \n" + e.getStackTrace());
			return false;
		}
	}

	@Override
	public boolean delBookStore(int bookStoreId) {

		String delBookStoreQuery = "DELETE from BookStores WHERE id = " + bookStoreId;

		LOGGER.info("Deleting bookstore from database...");

		try {
			statement.executeQuery(delBookStoreQuery);
			LOGGER.info("Successfully deleted bookstore from database.");
			return true;
		} catch (SQLException e) {
			LOGGER.error("Fail when deleting bookstore from database caused by: " + e.getStackTrace());
			return false;
		}

	}

	@Override
	public List<BookStore> getAllBooksStores() {

		String getListOfBookStoresQuery = "SELECT * FROM BookStores";

		List<BookStore> listOfBookStores = new ArrayList<>();

		LOGGER.info("Getting all bookstores from base...");

		try {

			ResultSet result = statement.executeQuery(getListOfBookStoresQuery);
			while (result.next()) {

				int id = result.getInt(1);
				String name = result.getString(2);
				String url = result.getString(3);
				String tag = result.getString(4);
				String container = result.getString(5);
				String nameTag = result.getString(6);
				String priceTag = result.getString(7);
				String nextTag = result.getString(8);

				BookStore bookStore = new BookStore(name, url, tag, container, nameTag, priceTag, nextTag);
				bookStore.setId(id);
				listOfBookStores.add(bookStore);
			}
			LOGGER.info("Successfully collected all the bookstores from the database.");
			result.close();

			return listOfBookStores;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Fail to collect list of bookstores from database.");
			return null;
		}
	}

	@Override
	public BookStore getBookStoreById(int bookStoreId) {
		String getBooksQuery = "SELECT * FROM Books WHERE id = '" + bookStoreId + "';";

		LOGGER.info("Getting bookStore " + bookStoreId + " from database...");

		try {
			ResultSet result = statement.executeQuery(getBooksQuery);
			result.next();

			int id = result.getInt(1);
			String name = result.getString(2);
			String url = result.getString(3);
			String tag = result.getString(4);
			String container = result.getString(5);
			String nameTag = result.getString(6);
			String priceTag = result.getString(7);
			String nextTag = result.getString(8);

			BookStore bookStore = new BookStore(name, url, tag, container, nameTag, priceTag, nextTag);
			bookStore.setId(id);
			result.close();

			LOGGER.info("Successfully collected bookStore " + bookStoreId + " from database.");

			return bookStore;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Fail when try to collect bookStore " + bookStoreId + " form database.");
			return null;
		}
	}

}
