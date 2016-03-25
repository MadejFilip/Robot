package com.epam.ja.kmw.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.BookDao;
import com.epam.ja.kmw.model.Book;

public class BookDaoImpl implements BookDao {

	private static final Logger LOGGER = LogManager.getLogger(BookDaoImpl.class);
	private ConnectionDao connectionDao;
	
	public BookDaoImpl(ConnectionDao connectionDao) {
		this.connectionDao=connectionDao;
		createTableBooks();
	}


	public boolean addBook(Book book) {
		String addBookQuery = "INSERT INTO Books VALUES (NULL, ?, ?, ?)";

		LOGGER.info("Adding book to database...");

		java.util.Date date = new java.util.Date();
		Date sqlDate = new Date(date.getTime());

		try {
			PreparedStatement prepareStatement = connectionDao.getConnection().prepareStatement(addBookQuery);
			prepareStatement.setString(1, book.getTitle());
			prepareStatement.setString(2, book.getBookStore());
			prepareStatement.setDate(3, sqlDate);
			prepareStatement.executeUpdate();
			LOGGER.info("Successfully added book to database.");
			return true;
		} catch (SQLException e) {
			LOGGER.error("Fail when adding book to databse. Caused by: \n" + e.getStackTrace());
			return false;
		}

	}
	
	private void createTableBooks() {
		String createBooksTableQuery = "CREATE TABLE IF NOT EXISTS Books (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " Title varchar(255), BookStore varchar(255), add_date datetime default current_datetime)";
		try {

			connectionDao.getStatement().execute(createBooksTableQuery);
			LOGGER.info("Created Book database");

		} catch (SQLException e) {
			LOGGER.error("Fail to create a table 'Book' in database. :" + e.getMessage());
		}

}

	public boolean addAllBooks(List<Book> listOfBooks) {

		for (Book book : listOfBooks) {
			if (!addBook(book))
				return false;
		}
		return true;
	}

	public boolean updateBook(Book book) {
		String addBookQuery = "UPDATE Books SET Title = ?, BookStore = ?, add_date = ? WHERE id = ?";

		LOGGER.info("Updating book in database...");

		java.util.Date date = new java.util.Date();
		Date sqlDate = new Date(date.getTime());

		try {
			PreparedStatement prepareStatement = connectionDao.getConnection().prepareStatement(addBookQuery);
			prepareStatement.setString(1, book.getTitle());
			prepareStatement.setString(2, book.getBookStore());
			prepareStatement.setDate(3, sqlDate);
			prepareStatement.setInt(4, book.getId());
			prepareStatement.executeUpdate();
			LOGGER.info("Successfully updated book in database.");
			return true;
		} catch (SQLException e) {
			LOGGER.error("Fail when updating book in databse. Caused by: \n" + e.getStackTrace());
			return false;
		}

	}

	public boolean delBook(int bookId) {

		String delBookQuery = "DELETE From Books WHERE id = " + bookId;
		LOGGER.info("Deleting book from database...");

		try {
			connectionDao.getStatement().executeQuery(delBookQuery);
			LOGGER.info("Successfully deleted book from database.");
		} catch (SQLException e) {
			LOGGER.error("Fail when deleting book from database. Caused by: \n" + e.getStackTrace());
		}
		return false;

	}

	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();

		String getListOfBooksQuery = "SELECT * FROM Books";

		LOGGER.info("Getting all books from base...");

		try {

			ResultSet result = connectionDao.getStatement().executeQuery(getListOfBooksQuery);
			while (result.next()) {

				int id = result.getInt(1);
				String title = result.getString(2);
				String bookStore = result.getString(3);

				Book book = new Book(title, bookStore);
				books.add(book);
				book.setId(id);
			}
			LOGGER.info("Successfully collected all the books from the database.");
			result.close();

			return books;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Fail to collect list of books from database.");
			return null;
		}
	}

	public Book getBookById(int bookId) {
		String getBooksQuery = "SELECT * FROM Books WHERE id = '" + bookId + "';";

		LOGGER.info("Getting book " + bookId + " from database...");

		try {
			ResultSet result = connectionDao.getStatement().executeQuery(getBooksQuery);
			result.next();

			String title = result.getString(1);
			String bookStore = result.getString(2);

			Book book = new Book(title, bookStore);

			result.close();

			LOGGER.info("Successfully collected book " + bookId + " from database.");

			return book;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Fail when try to collect book " + bookId + " form database.");
			return null;
		}

	}

	@Override
	public List<Book> getAllBooksForOneBookStore(String bookStoreName) {
		List<Book> books = new ArrayList<>();

		String getListOfBooksQuery = "SELECT * FROM Books WHERE bookStore = '" + bookStoreName + "';";

		LOGGER.info("Getting books from base for " + bookStoreName + " bookstore...");

		try {

			ResultSet result = connectionDao.getStatement().executeQuery(getListOfBooksQuery);
			while (result.next()) {

				int id = result.getInt(1);
				String title = result.getString(2);
				String bookStore = result.getString(3);

				Book book = new Book(title, bookStore);
				books.add(book);
				book.setId(id);
			}
			LOGGER.info("Successfully collected books from the database for " + bookStoreName + " bookstore.");
			result.close();

			return books;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Fail to collect list of books from database for " + bookStoreName + "bookstore.");
			return null;
		}
	}

}
