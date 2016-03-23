package com.epam.ja.kmw.main;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
	private static int RUN_COUNTER=0;
	private static String LAST_DATE="sdsd";
	
	public static void main(final String... args) {  
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage("/home/kj/icon.png"),"BookStoreRobot",popup);
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
        
        
        
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh");
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
               
        
        try{
            tray.add(trayIcon);
        }catch(AWTException awtException){
           LOGGER.error(awtException.getMessage());
        }
        
        downloading();
//        Timer timer = new Timer ();
//        TimerTask hourlyTask = new TimerTask () {
//            @Override
//            public void run () {
//            	Date curDate = new Date();
//               if(RUN_COUNTER <7 && hourFormat.format(curDate).equals("05") && !LAST_DATE.equals(dateFormat.format(curDate))){
//               downloading();
//               LAST_DATE=dateFormat.format(curDate);
//            }
//        }};
//
//        // schedule the task to run starting now and then every hour...
//        timer.schedule (hourlyTask, 0l,1000*60*10);

        
        
        
	}
	

	private static void downloading() {
		BookDaoImpl bookDao = new BookDaoImpl();

		// Set up a simple configuration that logs on the console.

		LOGGER.trace("Starting our great robot application.");

		BookStore lib = new BookStore("Nexto", "http://www.nexto.pl/ebooki_c1015.xml",
				"<a class=\"title\">", "<strong class=\"nprice\">", "<a class=\"next\">", "0,00");

		BookStore lib2 = new BookStore("BookRix", "http://www.bookrix.com/books.html", "<a class=\"word-break\">",
				"<p class=\"item-price\">", "<li class=\"next\">", "For Free");
		
		BookStore lib3 = new BookStore("Upoluj Ebooka","http://upolujebooka.pl/kategoria,8248,darmowe_e-booki.html","<h2>","<span itemprop=\"price\">","<a class=\"normal\">", "---");

		List<BookStore> libraries = new ArrayList<>();
		libraries.add(lib);
		libraries.add(lib2);
		libraries.add(lib3);
		// TODO libraries.addAll(from datebase);

		List<Book> bookList = new ArrayList<>();
		for (BookStore bookStore : libraries) {
				bookList.addAll(new LibraryChecker(bookStore).getFreeBooks());
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
