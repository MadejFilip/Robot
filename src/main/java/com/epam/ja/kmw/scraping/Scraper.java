package com.epam.ja.kmw.scraping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.impl.BookDaoImpl;
import com.epam.ja.kmw.dao.impl.BookStoreDaoImpl;
import com.epam.ja.kmw.model.BookStore;

public class Scraper {
	public static final Logger LOGGER = LogManager.getLogger(Scraper.class);

	@SuppressWarnings("unused")
	private List<BookStore> getBookStores() throws Exception {
		try (BookStoreDaoImpl bookStoreDaoImpl = new BookStoreDaoImpl()) {
			bookStoreDaoImpl.createConnection();
			bookStoreDaoImpl.createTable();
			return bookStoreDaoImpl.getAllBooksStores();
		}

	}

	public void downloading() {
		BookStore lib = new BookStore("Nexto", "http://www.nexto.pl/ebooki_c1015.xml", "<a class=\"title\">",
				"<strong class=\"nprice\">", "<a class=\"next\">", "0,00");

		BookStore lib2 = new BookStore("BookRix", "http://www.bookrix.com/books.html", "<a class=\"word-break\">",
				"<p class=\"item-price\">", "<li class=\"next\">", "For Free");

		BookStore lib3 = new BookStore("Upoluj Ebooka", "http://upolujebooka.pl/kategoria,8248,darmowe_e-booki.html",
				"<h2>", "<span itemprop=\"price\">", "<a class=\"normal\">", "---");
		try (BookDaoImpl bookDaoImpl = new BookDaoImpl()) {
			bookDaoImpl.createConnection();
			bookDaoImpl.createTable();
			
			List<BookStore> libraries = new ArrayList<>();
			libraries.add(lib);
			libraries.add(lib2);
			libraries.add(lib3);
			//List<BookStore> libraries = getBookStores();
			CountDownLatch latch = new CountDownLatch(libraries.size());
			for (BookStore bookStore : libraries) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						bookDaoImpl.addAllBooks(new LibraryChecker(bookStore).getFreeBooks());
						latch.countDown();
					}
				}).start();
			}
			latch.await();
		} catch (Exception e) {
			LOGGER.error("Couldn't close database: "+e.getMessage());
		}
	}

}
