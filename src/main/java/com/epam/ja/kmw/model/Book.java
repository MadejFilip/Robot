package com.epam.ja.kmw.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {

	private IntegerProperty id;
	private StringProperty title;
	private StringProperty bookStore;
	private StringProperty author;

	public Book(String title, String bookStore, String author) {
		id = new SimpleIntegerProperty(0);
		this.title = new SimpleStringProperty(title);
		this.bookStore = new SimpleStringProperty(bookStore);
		this.author = new SimpleStringProperty(author);

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

	public String getAuthor() {
		return author.get();
	}

	public void setAuthor(String author) {
		this.author.set(author);
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

	@Override
	public String toString() {
		return "Book [ id= " + id.get() + ", title= " + title.get() + ", bookStore= " + bookStore.get() + " ]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookStore == null) ? 0 : bookStore.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (bookStore == null) {
			if (other.bookStore != null)
				return false;
		} else if (!bookStore.equals(other.bookStore))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
