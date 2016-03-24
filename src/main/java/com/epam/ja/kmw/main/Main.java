package com.epam.ja.kmw.main;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	public static final Logger LOGGER = LogManager.getLogger(Main.class);

	public static void main(String... args) {
		
		TrayApp trayApp =new TrayApp(args);
		trayApp.initializeTray();
		
		Timer timer = new Timer();
		TimerTask timeChecker = new TimeChecker();
		timer.schedule(timeChecker, 0l, 1000 * 60 * 10);

	}
}
