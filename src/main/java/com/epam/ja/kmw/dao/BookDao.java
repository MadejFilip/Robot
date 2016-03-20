package com.epam.ja.kmw.dao;

import java.util.List;

import com.epam.ja.kmw.model.Book;

public interface BookDao {

	public void addBook(Book book);
	
	public void updateBook (Book book);
	
	public void delBook(int bookId);
	
	public List<Book> getAllBooks();
	
	public Book getBookById(int bookId);
}
