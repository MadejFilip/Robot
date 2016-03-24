package com.epam.ja.kmw.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.BookStoreDao;
import com.epam.ja.kmw.model.BookStore;

public class BookStoreDaoImpl extends AbstracDaoImpl implements BookStoreDao {

	private static final Logger LOGGER = LogManager.getLogger(BookDaoImpl.class);

	public void createTable() {

		String createBookStoresTableQuery = "CREATE TABLE IF NOT EXISTS BookStores (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name varchar(255), url varchar(255), nameTag varchar(255), priceTag varchar(255), "
				+ "nextTag varchar(255), add_date datetime default current_datetime)";
		try {
			boolean execute = statement.execute(createBookStoresTableQuery);
			if (!execute) {
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

		String addBoookStoreQuery = "INSERT INTO BookStores VALUES (NULL, ?, ?, ?, ?, ?, ?)";

		LOGGER.info("Adding bookstore to database...");

		java.util.Date date = new java.util.Date();
		Date sqlDate = new Date(date.getTime());

		try {

			PreparedStatement prepareStatement = connection.prepareStatement(addBoookStoreQuery);

			prepareStatement.setString(1, bookStore.getName());
			prepareStatement.setString(2, bookStore.getUrl());
			prepareStatement.setString(3, bookStore.getNameTag());
			prepareStatement.setString(4, bookStore.getPriceTag());
			prepareStatement.setString(5, bookStore.getNextTag());
			prepareStatement.setDate(6, sqlDate);

			prepareStatement.executeUpdate();

			LOGGER.info("Successfully add bookstore to database.");
		} catch (SQLException e) {
			LOGGER.error("Fail when adding bookstore to database caused by: " + e.getStackTrace());
		}

		return false;
	}

	@Override
	public boolean updateBookStore(BookStore bookStore) {
		String updateBookStoreQuery = "UPDATE BookStores SET name = ?, url = ?, nameTag = ?, "
				+ "priceTag = ?, nextTag = ?  WHERE id = ?";

		LOGGER.info("Updating book in database...");

		try {
			PreparedStatement prepareStatement = connection.prepareStatement(updateBookStoreQuery);
			prepareStatement.setString(1, bookStore.getName());
			prepareStatement.setString(2, bookStore.getUrl());
			prepareStatement.setString(3, bookStore.getNameTag());
			prepareStatement.setString(4, bookStore.getPriceTag());
			prepareStatement.setString(5, bookStore.getNextTag());
			prepareStatement.setInt(6, bookStore.getId());
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
				String nameTag = result.getString(4);
				String priceTag = result.getString(5);
				String nextTag = result.getString(6);
				String priceValue = result.getString(7);

				BookStore bookStore = new BookStore(name, url, nameTag, priceTag, nextTag, priceValue);
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
			String nameTag = result.getString(4);
			String priceTag = result.getString(5);
			String nextTag = result.getString(6);
			String priceValue = result.getString(7);

			BookStore bookStore = new BookStore(name, url, nameTag, priceTag, nextTag, priceValue);
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
