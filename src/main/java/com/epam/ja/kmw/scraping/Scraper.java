package com.epam.ja.kmw.scraping;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.impl.BookDaoImpl;
import com.epam.ja.kmw.dao.impl.BookStoreDaoImpl;
import com.epam.ja.kmw.model.BookStore;

public class Scraper {
	public static final Logger LOGGER = LogManager.getLogger(Scraper.class);

	private List<BookStore> getBookStores() throws Exception {
		try (BookStoreDaoImpl bookStoreDaoImpl = new BookStoreDaoImpl()) {
			bookStoreDaoImpl.createConnection();
			bookStoreDaoImpl.createTable();
			return bookStoreDaoImpl.getAllBooksStores();
		}

	}

	public boolean downloading() {
			boolean result = true;
		try (BookDaoImpl bookDaoImpl = new BookDaoImpl()) {
			bookDaoImpl.createConnection();
			bookDaoImpl.createTable();

			List<BookStore> libraries = getBookStores();
			if(libraries.size()==0){
				result=false;
			}
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
			LOGGER.error("Couldn't close database: " + e.getMessage());
		}
		return result;
	}

}
