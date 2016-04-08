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

public class BookStoreDaoImpl implements BookStoreDao {

	private static final Logger LOGGER = LogManager.getLogger(BookDaoImpl.class);
	ConnectionDao connectionDao;

	public BookStoreDaoImpl(ConnectionDao connectionDao) {
		this.connectionDao = connectionDao;
		createTableBookStores();
	}

	private void createTableBookStores() {

		String createBookStoresTableQuery = "CREATE TABLE IF NOT EXISTS BookStores (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name varchar(255), url varchar(255), nameTag varchar(255), priceTag varchar(255), "
				+ "nextTag varchar(255), priceValue varchar(255), add_date datetime default current_datetime, authorTag varchar(255), tagsTag varchar(255), type varchar(255))";
		try {

			connectionDao.getStatement().executeQuery(createBookStoresTableQuery);
			LOGGER.info("Created BookStore database");
		} catch (SQLException e) {
			LOGGER.error("Fail to create a table 'BookStore' in database. :" + e.getMessage());
		}
	}

	@Override
	public boolean addBookStore(BookStore bookStore) {

		String addBoookStoreQuery = "INSERT INTO BookStores VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		LOGGER.info("Adding bookstore to database...");

		java.util.Date date = new java.util.Date();
		Date sqlDate = new Date(date.getTime());

		try {

			PreparedStatement prepareStatement = connectionDao.getConnection().prepareStatement(addBoookStoreQuery);

			prepareStatement.setString(1, bookStore.getName());
			prepareStatement.setString(2, bookStore.getUrl());
			prepareStatement.setString(3, bookStore.getNameTag());
			prepareStatement.setString(4, bookStore.getPriceTag());
			prepareStatement.setString(5, bookStore.getNextTag());
			prepareStatement.setString(6, bookStore.getPriceValue());

			prepareStatement.setDate(7, sqlDate);
			prepareStatement.setString(8, bookStore.getAuthorTag());
			prepareStatement.setString(9, bookStore.getTagsTag());
			prepareStatement.setString(10, bookStore.getType());
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
				+ "priceTag = ?, nextTag = ?, priceValue = ?, authorTag = ?, tagsTag = ?, type  = ?    WHERE id = ?";

		LOGGER.info("Updating book in database...");

		try {
			PreparedStatement prepareStatement = connectionDao.getConnection().prepareStatement(updateBookStoreQuery);
			prepareStatement.setString(1, bookStore.getName());
			prepareStatement.setString(2, bookStore.getUrl());
			prepareStatement.setString(3, bookStore.getNameTag());
			prepareStatement.setString(4, bookStore.getPriceTag());
			prepareStatement.setString(5, bookStore.getNextTag());
			prepareStatement.setString(6, bookStore.getPriceValue());
			prepareStatement.setInt(7, bookStore.getId());
			prepareStatement.setString(8, bookStore.getAuthorTag());
			prepareStatement.setString(9, bookStore.getTagsTag());
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
			connectionDao.getStatement().executeQuery(delBookStoreQuery);
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

			ResultSet result = connectionDao.getStatement().executeQuery(getListOfBookStoresQuery);
			while (result.next()) {

				int id = result.getInt(1);
				String name = result.getString(2);
				String url = result.getString(3);
				String nameTag = result.getString(4);
				String priceTag = result.getString(5);
				String nextTag = result.getString(6);
				String priceValue = result.getString(7);
				String authorTag = result.getString(9);
				String tagsTag = result.getString(10);
				String type = result.getString(11);
				BookStore bookStore = new BookStore(name, url, nameTag, priceTag, nextTag, priceValue, authorTag,tagsTag,type);

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
			ResultSet result = connectionDao.getStatement().executeQuery(getBooksQuery);
			result.next();

			int id = result.getInt(1);
			String name = result.getString(2);
			String url = result.getString(3);
			String nameTag = result.getString(4);
			String priceTag = result.getString(5);
			String nextTag = result.getString(6);
			String priceValue = result.getString(7);

			String authorTag = result.getString(9);
			String tagsTag = result.getString(10);
			String type = result.getString(11);
			BookStore bookStore = new BookStore(name, url, nameTag, priceTag, nextTag, priceValue, authorTag,tagsTag,type);
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

	public BookStore getBookStoreByName(String bookStoreName) {
		String getBooksQuery = "SELECT * FROM BookStores WHERE name = '" + bookStoreName + "';";

		LOGGER.info("Getting bookStore " + bookStoreName + " from database...");

		try {
			ResultSet result = connectionDao.getStatement().executeQuery(getBooksQuery);
			result.next();

			int id = result.getInt(1);
			String url = result.getString(3);
			String nameTag = result.getString(4);
			String priceTag = result.getString(5);
			String nextTag = result.getString(6);
			String priceValue = result.getString(7);
			String authorTag = result.getString(9);
			String tagsTag = result.getString(10);
			String type = result.getString(11);
			BookStore bookStore = new BookStore(bookStoreName, url, nameTag, priceTag, nextTag, priceValue, authorTag,tagsTag,type);

			bookStore.setId(id);
			result.close();

			LOGGER.info("Successfully collected bookStore " + bookStoreName + " from database.");

			return bookStore;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Fail when try to collect bookStore " + bookStoreName + " form database.");
			return null;
		}
	}

}
