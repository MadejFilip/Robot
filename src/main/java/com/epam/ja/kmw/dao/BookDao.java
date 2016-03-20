package com.epam.ja.kmw.dao;

import java.util.List;

import com.epam.ja.kmw.model.Book;

public interface BookDao {

	public boolean addBook(Book book);
	
	public boolean updateBook (Book book);
	
	public boolean delBook(int bookId);
	
	public List<Book> getAllBooks();
	
	public Book getBookById(int bookId);
}
