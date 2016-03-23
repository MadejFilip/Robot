package com.epam.ja.kmw.model;

public class Properties {

	private int runCounter = 0;
	private String lastDate = "";

	public Properties(String lastDate, int runCounter) {
		this.lastDate = lastDate;
		this.runCounter = runCounter;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public int getRunCounter() {
		return runCounter;
	}

	public void setRunCounter(int runCounter) {
		this.runCounter = runCounter;
	}

}
