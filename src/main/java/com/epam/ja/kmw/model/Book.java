package com.epam.ja.kmw.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {

	private IntegerProperty id;
	private StringProperty title;
	private StringProperty bookStore;

	public Book(String title, String bookStore) {
		id = new SimpleIntegerProperty(0);
		this.title = new SimpleStringProperty(title);
		this.bookStore = new SimpleStringProperty(bookStore);
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public StringProperty titleProperty() {
		return title;
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public StringProperty bookStoreProperty() {
		return bookStore;
	}

	public String getBookStore() {
		return bookStore.get();
	}

	public void setBookStore(String bookStore) {
		this.bookStore.set(bookStore);
	}
}
