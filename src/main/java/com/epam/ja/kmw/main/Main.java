package com.epam.ja.kmw.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.impl.BookDaoImpl;
import com.epam.ja.kmw.model.Book;
import com.epam.ja.kmw.model.BookStore;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;

import scraping.LibraryChecker;

public class Main {

	public static final Logger LOGGER = LogManager.getLogger(Main.class);

	public static void main(final String... args) {

		BookDaoImpl bookDao = new BookDaoImpl();

		// Set up a simple configuration that logs on the console.

		LOGGER.trace("Starting our great robot application.");

		BookStore lib = new BookStore("www.nexto.pl", "http://www.nexto.pl/ebooki_c1015.xml",
				"<ul class=\"productslist\">", "<li>", "<h3>", "<strong class=\"nprice\">", "<a class=\"next\">");

		BookStore lib2 = new BookStore("bookrix.com", "http://www.bookrix.com/books.html", "<section id=\"booksList\">",
				"<div class=\"item\">", "<a class=\"word-break\">", "<p class=\"item-price\">", "<li class=\"next\">");

		List<BookStore> libraries = new ArrayList<>();
		libraries.add(lib);
		libraries.add(lib2);
		// TODO libraries.addAll(from datebase);

		List<Book> bookList = new ArrayList<>();
		for (BookStore bookStore : libraries) {
			try {
				bookList.addAll(LibraryChecker.getFreeBooks(bookStore));
			} catch (NotFound | ResponseException e) {
				LOGGER.error(e.getMessage());
			}
		}

		bookDao.createConnection();
		bookDao.createTable();

		for (Book book : bookList) {
			bookDao.addBook(book);
		}

		List<Book> allBooks = bookDao.getAllBooks();

		for (Book book : allBooks) {
			System.out.println(book);
		}

		bookDao.closeConnection();
	}
}
