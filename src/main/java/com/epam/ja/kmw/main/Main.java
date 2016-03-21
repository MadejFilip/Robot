package com.epam.ja.kmw.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.model.Book;
import com.epam.ja.kmw.model.BookStore;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;

import scraping.LibraryChecker;

public class Main {

	public static final Logger LOGGER = LogManager.getLogger(Main.class);

	public static void main(final String... args) {

		// Set up a simple configuration that logs on the console.

		LOGGER.trace("Starting our great robot application.");
		BookStore lib = new BookStore("library", "http://www.nexto.pl/ebooki/darmowe_c1219.xml?_offset=10",
				"<ul class=\"productslist\">", "<li>", "<h3>", "<strong class=\"nprice\">");
		
		Book[] b = null;
		try {
			b = LibraryChecker.getFreeBooks(lib);
		} catch (NotFound | ResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for(Book bk : b) {
			System.out.println(bk);
		}
		

	}
}
