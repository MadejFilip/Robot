package com.epam.ja.kmw.dao;

import java.util.List;

import com.epam.ja.kmw.model.BookStore;

public interface BookStoreDao {

	public boolean addBookStore(BookStore bookStore);

	public boolean updateBookStore(BookStore bookStore);

	public boolean delBookStore(int bookStoreId);

	public List<BookStore> getAllBooksStores();

	public BookStore getBookStoreById(int bookStoreId);

}
