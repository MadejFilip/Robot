package com.epam.ja.kmw.model;

public class Properties {

	private int runCounter = 0;
	private String lastDate = "";

	/**
	 * Creates Properties object and initializes it.
	 * 
	 * @param lastDate
	 *            date of last downloading.
	 * @param runCounter
	 *            amount of downloading.
	 */
	public Properties(String lastDate, int runCounter) {
		this.lastDate = lastDate;
		this.runCounter = runCounter;
	}

	/**
	 * Returned object is a String and stores date.
	 * 
	 * @return date of last downloading.
	 */
	public String getLastDate() {
		return lastDate;
	}

	/**
	 * Sets date of last downloading in a Properties object.
	 * 
	 * @param lastDate
	 *            used to set date of last downloading.
	 */
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * Returned object is an integer and stores amount of downloading.
	 * 
	 * @return amount of downloading.
	 */
	public int getRunCounter() {
		return runCounter;
	}

	/**
	 * sets amount of downloading in Properties object.
	 * 
	 * @param runCounter
	 *            used to set amount of downloading.
	 */
	public void setRunCounter(int runCounter) {
		this.runCounter = runCounter;
	}

}
