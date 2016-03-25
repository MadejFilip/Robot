package com.epam.ja.kmw.scraping;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.impl.BookDaoImpl;
import com.epam.ja.kmw.dao.impl.BookStoreDaoImpl;
import com.epam.ja.kmw.dao.impl.ConnectionDao;
import com.epam.ja.kmw.model.BookStore;

public class Scraper {
	public static final Logger LOGGER = LogManager.getLogger(Scraper.class);

	private List<BookStore> getBookStores() throws SQLException{
		try (ConnectionDao connectionDao = new ConnectionDao()) {
			BookStoreDaoImpl bookStoreDaoImpl = new BookStoreDaoImpl(connectionDao);
			return bookStoreDaoImpl.getAllBooksStores();
		}

	}

	public boolean downloading() {
			boolean result = true;
		try (ConnectionDao connectionDao = new ConnectionDao()) {
			BookDaoImpl bookDaoImpl = new BookDaoImpl(connectionDao);


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
		} catch (SQLException e) {
			LOGGER.error("Couldn't close database: " + e.getMessage());
		} catch (InterruptedException e) {
			LOGGER.error("Thread exception" + e);
		}
		return result;
	}

}
