package com.epam.ja.kmw;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.main.TimeChecker;
import com.epam.ja.kmw.main.TrayApp;
import com.epam.ja.kmw.viewer.FreeBookViewer;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static final Logger LOGGER = LogManager.getLogger(Main.class);

	private Stage thisStage;
	FreeBookViewer freeBookViewer;

	public static void main(String... args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		thisStage = primaryStage;
		freeBookViewer = new FreeBookViewer(thisStage);

		TrayApp trayApp = new TrayApp();
		trayApp.initializeTray(freeBookViewer);
		Timer timer = new Timer();
		TimerTask timeChecker = new TimeChecker(thisStage);
		timer.schedule(timeChecker, 0l, 1000 * 60);

	}
}