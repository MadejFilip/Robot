package com.epam.ja.kmw.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.impl.ConnectionDao;
import com.epam.ja.kmw.dao.impl.PropertiesDaoImpl;
import com.epam.ja.kmw.model.Properties;
import com.epam.ja.kmw.scraping.Scraper;

import javafx.application.Platform;
import javafx.stage.Stage;

public class TimeChecker extends TimerTask {
	public static final Logger LOGGER = LogManager.getLogger(TimeChecker.class);
	SimpleDateFormat hourFormat = new SimpleDateFormat("kk");
	SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
	Stage primaryStage;

	public TimeChecker(Stage thisStage) {
		super();
		this.primaryStage = thisStage;
	}

	@Override
	public void run() {
		Date curDate = new Date();
		try (ConnectionDao connectionDao = new ConnectionDao()) {
			
			PropertiesDaoImpl propertiesDaoImpl = new PropertiesDaoImpl(connectionDao);
			Properties properties = propertiesDaoImpl.getProperties();
			/*if (properties.getRunCounter() < 7 && hourFormat.format(curDate).equals("14")
					&& !properties.getLastDate().equals(dateFormat.format(curDate))) */
			Thread.sleep(10000);
			if(true){
				TrayApp.changeOpeningStatus();
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						primaryStage.hide();
					}
				});
				JOptionPane.showMessageDialog(null,
						"BookStoreRobot is curently downloading data... \nCheck in couple of minutes");

				properties.setLastDate(dateFormat.format(curDate));
				properties.setRunCounter(properties.getRunCounter() + 1);
				if (new Scraper().downloading())
					propertiesDaoImpl.updateProperties(properties);
				TrayApp.changeOpeningStatus();
			}
		} catch (Exception e) {
			LOGGER.error("Can't close Properties database connection" + e.getMessage());
		}

	}

}
