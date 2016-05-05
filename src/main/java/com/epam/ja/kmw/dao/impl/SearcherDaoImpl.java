package com.epam.ja.kmw.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.model.Book;

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
		createTableBooks();
	}

	/**
	 * Creates table named Books in a database if it doesn't exists. If
	 * executing statement fails method catches SQLException.
	 */
	private void createTableBooks() {
		String createBooksTableQuery = "CREATE TABLE IF NOT EXISTS Books (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " Title varchar(255), BookStore varchar(255), add_date datetime default current_datetime, Author varchar(255),  Tags varchar(255) )";
		try {

			connectionDao.getStatement().execute(createBooksTableQuery);
			LOGGER.info("Created Book database");

		} catch (SQLException e) {
			LOGGER.error("Fail to create a table 'Book' in database. :" + e.getMessage());
		}

	}

	public List<Book> getBooksByTagAndDate(String bookStoreName, String bookTag, Date bookDate) {
		List<Book> books = new ArrayList<>();

		String getListOfBooksQuery = "SELECT * FROM Books WHERE bookStore = '" + bookStoreName + "' AND '" + bookTag
				+ "' AND '" + bookDate + "';";

		LOGGER.info("Getting books with " + bookTag + " tag...");

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
			LOGGER.info("Successfully collected books from the database for " + bookStoreName + " bookstore.");

			return books;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Fail to collect list of books from database for " + bookStoreName + "bookstore.");
			return null;
		}
	}

}
