package com.epam.ja.kmw.dao;

import java.util.List;

import com.epam.ja.kmw.model.Book;

public interface BookDao {

	/**
	 * Adds to database a book and returns true. If connection fails method
	 * catches SQLException and returns false.
	 * 
	 * @param book
	 *            object that will be added.
	 * @return true if operation succeed, false if not.
	 */
	public boolean addBook(Book book);

	/**
	 * Adds to database list of books and returns true. If connection fails
	 * method catches SQLException and returns false.
	 * 
	 * @param listOfBooks
	 *            list of objects that will be added.
	 * @return true if operation succeed, false if not.
	 */
	public boolean addAllBooks(List<Book> listOfBooks);

	/**
	 * Updates a book in a database and returns true. If connection fails method
	 * catches SQLException and returns false.
	 * 
	 * @param book
	 *            object that will be updated.
	 * @return true if operation succeed, false if not.
	 */
	public boolean updateBook(Book book);

	/**
	 * Deletes a book stored in a database and which ID is sent as an argument
	 * and returns false. If connection fails method catches SQLException.
	 * 
	 * @param bookId
	 *            ID of a book that will be deleted.
	 * @return false if operation succeed.
	 */
	public boolean delBook(int bookId);

	/**
	 * Returned container is a list of Book class objects stored in a database.
	 * If connection fails method catches SQLException and returns null.
	 * 
	 * @return a list of all books contained in a database.
	 */
	public List<Book> getAllBooks();

	/**
	 * Returned container is a list of Book class objects from one specific
	 * bookstore stored in a database. If connection fails method catches
	 * SQLException and returns null.
	 * 
	 * @param bookStoreName
	 *            name of a bookstore from which books will be returned.
	 * @return a list of all books contained in one specific bookstore in a
	 *         database.
	 */
	public List<Book> getAllBooksForOneBookStore(String bookStoreName);

	/**
	 * Returned object is stored in a database and is an instance of a Book
	 * class. It is selected by its ID. If connection fails method catches
	 * SQLException and returns null.
	 * 
	 * @param bookId
	 *            ID of a book that will be returned.
	 * @return a specific book by its ID.
	 */
	public Book getBookById(int bookId);

}
