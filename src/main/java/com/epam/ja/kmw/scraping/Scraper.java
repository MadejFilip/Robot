package com.epam.ja.kmw.scraping;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.impl.BookDaoImpl;
import com.epam.ja.kmw.dao.impl.BookStoreDaoImpl;
import com.epam.ja.kmw.model.BookStore;

/**
 * @author filipm Provides functions needed to initialize downloading of books
 *         from library sites and saving them in database.
 */
public class Scraper {
	public static final Logger LOGGER = LogManager.getLogger(Scraper.class);

	public boolean downloading() {
		boolean result = true;
		BookDaoImpl bookDaoImpl = new BookDaoImpl();

		List<BookStore> libraries = new BookStoreDaoImpl().getAllBooksStores();

		if (libraries.size() == 0) {
			result = false;
		}
		try {
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
