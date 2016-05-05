package com.epam.ja.kmw.model;

import java.time.LocalDate;

public class Searcher {

	private String nameTag;
	private LocalDate date;

	public Searcher(String nameTag, LocalDate date) {
		this.nameTag = nameTag;
		this.date = date;
	}

	public String getNameTag() {
		return nameTag;
	}

	public void setNameTag(String nameTag) {
		this.nameTag = nameTag;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
