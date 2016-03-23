package com.epam.ja.kmw.main;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	public static final Logger LOGGER = LogManager.getLogger(Main.class);

	public static void main(final String... args) {

		LOGGER.trace("Starting our great robot application.");
		if (!SystemTray.isSupported()) {
			LOGGER.error("SystemTray is not supported");
			return;
		}
		final PopupMenu popup = new PopupMenu();
		final TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage("./src/main/resources/icon.png"),
				"BookStoreRobot", popup);
		trayIcon.setImageAutoSize(true);
		final SystemTray tray = SystemTray.getSystemTray();
		MenuItem openItem = new MenuItem("Open");
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		MenuItem closeItem = new MenuItem("Close");
		closeItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		popup.add(openItem);
		popup.add(closeItem);


		try {
			tray.add(trayIcon);
		} catch (AWTException awtException) {
			LOGGER.error(awtException.getMessage());
		}
	
		
		Timer timer = new Timer();
		TimerTask timeChecker = new TimeChecker();
		timer.schedule(timeChecker, 0l, 1000 * 60 * 10);

	}
}
