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
	DelegatorRun delegatorRun = new DelegatorRun();
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

	class DelegatorRun {
		public boolean DelegatorRun() {
			curDate = new Date();
			if (hourFormat.format(curDate).equals(timeStart)) {
				TimeChecker.this.run();
				return true;
			} else {
				return false;
			}

		}
	}

	@Override
	public void run() {
		curDate = new Date();
		if (hourFormat.format(curDate).equals(timeStart)) {
			if (new Scraper().downloading()) {
				LOGGER.trace("Start downloading books");
			} else {
				LOGGER.trace("Database dosen't have any bookStore");
			}
		}
	}

}
