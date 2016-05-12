package com.epam.ja.kmw.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.scraping.Scraper;

/**
 * @author filipm Provides function needed to run method downloading on specific
 *         time.
 */
public class TimeChecker extends TimerTask {
	public static final Logger LOGGER = LogManager.getLogger(TimeChecker.class);
	private SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
	private String timeStart;

	Date curDate;

	/**
	 * Creates TimeChecker object and initializes it.
	 * 
	 * @param timeStart
	 *            initialize Stage class object.
	 */
	public TimeChecker(String timeStart) {
		this.timeStart = timeStart;
	}

	@Override
	public void run() {
		System.out.println("start");
		curDate = new Date();
		if (hourFormat.format(curDate).equals(timeStart)) {
			System.out.println("start");
			if (new Scraper().downloading()) {
				System.out.println("start");
				LOGGER.trace("Start downloading books");
			} else {
				LOGGER.trace("Database dosen't have any bookStore");
			}
		}
	}

}
