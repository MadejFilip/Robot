package com.epam.ja.kmw.main;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
	private static int RUN_COUNTER;
	private static String LAST_DATE;
	public static void main(final String... args) {

		/*BookDaoImpl bookDao = new BookDaoImpl();

		// Set up a simple configuration that logs on the console.

		LOGGER.trace("Starting our great robot application.");

		BookStore lib = new BookStore("NEXTO", "http://www.nexto.pl/ebooki_c1015.xml",
				"<a class=\"title\">", "<strong class=\"nprice\">", "<a class=\"next\">");

		BookStore lib2 = new BookStore("BOOKRIX", "http://www.bookrix.com/books.html", "<section id=\"booksList\">",
				"<p class=\"item-price\">", "<li class=\"next\">");

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

		bookDao.closeConnection();*/
		
	       
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage("your_image/path_here.gif"),"blabla",popup);
        trayIcon.setImageAutoSize(true);
        final SystemTray tray = SystemTray.getSystemTray();
        MenuItem openItem = new MenuItem("Open");
        
        MenuItem closeItem = new MenuItem("Close");
        closeItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);				
			}
		});
        
        popup.add(openItem);
        popup.add(closeItem);
        
        
        
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("hh");
        String dateToStr = format.format(curDate);
        System.out.println(dateToStr);
        
        
        
        try{
            tray.add(trayIcon);
        }catch(AWTException awtException){
            awtException.printStackTrace();
        }
        
        while(true) {
        	if(dateToStr.equals("12")) {
        		
        		break;
        	}
        		
        }
        
	}
}
