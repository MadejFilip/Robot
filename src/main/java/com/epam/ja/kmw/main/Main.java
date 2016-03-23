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
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import scraping.Scraper;

public class Main {

	public static final Logger LOGGER = LogManager.getLogger(Main.class);
	private static int RUN_COUNTER=0;
	private static String LAST_DATE="sdsd";
	
	public static void main(final String... args) {  

		LOGGER.trace("Starting our great robot application.");
        if (!SystemTray.isSupported()) {
            LOGGER.error("SystemTray is not supported");
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
        
        Timer timer = new Timer ();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {
            	Date curDate = new Date();
               if(RUN_COUNTER <7 && hourFormat.format(curDate).equals("05") && !LAST_DATE.equals(dateFormat.format(curDate))){
               new Scraper().downloading();
               LAST_DATE=dateFormat.format(curDate);
            }
        }};

        // schedule the task to run starting now and then every hour...
       timer.schedule (hourlyTask, 0l,1000*60*10);

        
        
        
	}
	
}
