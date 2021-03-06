package com.epam.ja.kmw;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.controller.FreeBookViewer;
import com.epam.ja.kmw.controller.InicializateTrayAppControler;
import com.epam.ja.kmw.main.TimeChecker;
import com.epam.ja.kmw.model.MemoryClass;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Adam, Filip, Teams Bula
 * @version 1.1 Project Robots witch get information about Books.
 * 
 */
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

		InicializateTrayAppControler trayApp = new InicializateTrayAppControler();
		trayApp.initializeTray(freeBookViewer);
		Timer timer = new Timer();
		TimerTask timeChecker = new TimeChecker();
		timer.schedule(timeChecker, 0l, 500);

	}
}