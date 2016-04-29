package com.epam.ja.kmw.dao;

import java.util.List;

import com.epam.ja.kmw.model.BookStore;

public interface BookStoreDao {

	/**
	 * Adds to database a bookstore and returns true. If connection fails method
	 * catches SQLException and returns false.
	 * 
	 * @param bookStore
	 *            object that will be added
	 * @return true if operation succeed, false if not.
	 */
	public boolean addBookStore(BookStore bookStore);

	/**
	 * Updates a bookstore in a database and returns true. If connection fails
	 * method catches SQLException and returns false.
	 * 
	 * @param bookStore
	 *            object that will be updated.
	 * @return true if operation succeed, false if not.
	 */
	public boolean updateBookStore(BookStore bookStore);

	/**
	 * Deletes a bookstore stored in a database and which ID is sent as an
	 * argument and returns true. If connection fails method catches
	 * SQLException and returns false.
	 * 
	 * @param bookStoreId
	 *            ID of a bookstore that will be deleted.
	 * @return true if operation succeed, false if not.
	 */
	public boolean delBookStore(int bookStoreId);

	/**
	 * Returned container is a list of Bookstore class objects stored in a
	 * database. If connection fails method catches SQLException and returns
	 * null.
	 * 
	 * @return a list of all bookstores contained in a database.
	 */
	public List<BookStore> getAllBooksStores();

	/**
	 * Returned object is stored in a database and is an instance of a Bookstore
	 * class. It is selected by its ID. If connection fails method catches
	 * SQLException and returns null.
	 * 
	 * @param bookStoreId
	 *            ID of a bookstore that will be returned.
	 * @return a specific bookstore by its ID.
	 */
	public BookStore getBookStoreById(int bookStoreId);

}
