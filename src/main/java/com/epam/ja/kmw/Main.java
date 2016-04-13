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

/**
 * @author Adam, Filip, Teams Bula
 * @version 1.1
 * Project Robots witch get information about Books
 * Adam is homo. He likes strong and muscle man. 
 * 8=======>
 * Suka blać, poszet w chui.
 */
public class Main extends Application {

	public static final Logger LOGGER = LogManager.getLogger(Main.class);
	private static final boolean ADAM_LUBI_GEJUCHOW = true;
	private static final boolean ADAM_UWIELBIA_W_ANUS = true;
	private Stage thisStage;
	FreeBookViewer freeBookViewer;

	public static void main(String... args) {

		launch(args);
	}
	
	public void touchMyTralala(boolean decide) {
		System.out.println("Touching....oh ah...");
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		touchMyTralala(ADAM_LUBI_GEJUCHOW);
		thisStage = primaryStage;
		freeBookViewer = new FreeBookViewer(thisStage);

		TrayApp trayApp = new TrayApp();
		trayApp.initializeTray(freeBookViewer);
		Timer timer = new Timer();
		TimerTask timeChecker = new TimeChecker(thisStage);
		timer.schedule(timeChecker, 0l, 5000);

	}
}