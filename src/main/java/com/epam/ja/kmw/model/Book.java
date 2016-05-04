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
	private StringProperty tags;

	/**
	 * Creates object of a Book class and initializes it.
	 * 
	 * @param title
	 *            name of a created book which is stored in database.
	 * @param bookStore
	 *            name of a bookstore from which books were downloaded.
	 * @param author
	 *            name of a downloaded book author.
	 * @param tags
	 *            names of all downloaded book tags.
	 */
	public Book(String title, String bookStore, String author, String tags) {
		id = new SimpleIntegerProperty(0);
		this.title = new SimpleStringProperty(title);
		this.tags = new SimpleStringProperty(tags);
		this.bookStore = new SimpleStringProperty(bookStore);
		this.author = new SimpleStringProperty(author);

	}

	/**
	 * Returned object is an integer. This method calls the get() method from
	 * WritableIntegerValue interface.
	 * 
	 * @return ID of a specific book.
	 */
	public int getId() {
		return id.get();
	}

	/**
	 * Sets ID in a Book object. This method calls the set() method from
	 * WritableIntegerValue interface.
	 * 
	 * @param id
	 *            used to set ID of a specific book.
	 */
	public void setId(int id) {
		this.id.set(id);
	}

	/**
	 * Returned object is a String. This method calls the get() method from
	 * WritableObjectValue interface.
	 * 
	 * @return author of a specific book.
	 */
	public String getAuthor() {
		return author.get();
	}

	/**
	 * Sets author in a Book object. This method calls the set() method from
	 * WritableObjectValue interface.
	 * 
	 * @param author
	 *            used to set author of a specific book.
	 */
	public void setAuthor(String author) {
		this.author.set(author);
	}

	/**
	 * Returned object is a String. This method calls the get() method from
	 * WritableObjectValue interface.
	 * 
	 * @return tags of a specific book.
	 */
	public String getTags() {
		return tags.get();
	}

	/**
	 * Sets tags in a Book object. This method calls the set() method from
	 * WritableObjectValue interface.
	 * 
	 * @param tags
	 *            used to set tags of a specific book.
	 */
	public void setTags(String tags) {
		this.tags.set(tags);
	}

	/**
	 * Returned object is a String. This method calls the get() method from
	 * WritableObjectValue interface.
	 * 
	 * @return title of a specific book.
	 */
	public String getTitle() {
		return title.get();
	}

	/**
	 * Sets author in a Book object. This method calls the set() method from
	 * WritableObjectValue interface.
	 * 
	 * @param title
	 *            used to set title of a specific book.
	 */
	public void setTitle(String title) {
		this.title.set(title);
	}

	/**
	 * Returned object is a String. This method calls the get() method from
	 * WritableObjectValue interface.
	 * 
	 * @return bookstore name from which specific book is downloaded.
	 */
	public String getBookStore() {
		return bookStore.get();
	}

	/**
	 * Sets bookstore name in a Book object. This method calls the set() method
	 * from WritableObjectValue interface.
	 * 
	 * @param bookStore
	 *            used to set bookstore name from which specific book is
	 *            downloaded.
	 */
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
		if (bookStore.getValue() == null) {
			if (other.bookStore != null)
				return false;
		} else if (!bookStore.getValue().equals(other.bookStore.getValue()))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.getValue().equals(other.id.getValue()))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.getValue().equals(other.title.getValue()))
			return false;
		return true;
	}

}
