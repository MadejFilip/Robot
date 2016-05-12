package com.epam.ja.kmw.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.model.Book;

/**
 * @author filipm Provides function needed to search books by tags.
 */
public class SearcherDaoImpl {
	private static final Logger LOGGER = LogManager.getLogger(BookDaoImpl.class);
	private ConnectionDao connectionDao;

	/**
	 * Creates BookDaoImpl object and calls private method createTableBooks.
	 * 
	 * @param connectionDao
	 *            initialize created object.
	 */
	public SearcherDaoImpl(ConnectionDao connectionDao) {
		this.connectionDao = connectionDao;
	}

	/**
	 * Searches and returns books with specific tag. If connection fails method
	 * catches SQLException and returns null.
	 * 
	 * @param searchedTag
	 *            searched tag.
	 * @return list of books if operation succeeds, null if not.
	 */
	public List<Book> getBooksByTag(String searchedTag) {
		List<Book> books = new ArrayList<>();

		String getListOfBooksQuery = "SELECT * FROM Books WHERE Tags like '%" + searchedTag + "%'";

		LOGGER.info("Getting books from base for " + searchedTag + " book tags...");

		try (ResultSet result = connectionDao.getStatement().executeQuery(getListOfBooksQuery);) {

			while (result.next()) {

				int id = result.getInt(1);
				String title = result.getString(2);
				String bookStore = result.getString(3);
				String author = result.getString(5);
				String tags = result.getString(6);
				Book book = new Book(title, bookStore, author, tags);
				books.add(book);
				book.setId(id);
			}
			LOGGER.info("Successfully collected books from the database for " + searchedTag + " book tags.");

			return books;

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Fail to collect list of books from database for " + searchedTag + "book tags.");
			return null;
		}

	}

}