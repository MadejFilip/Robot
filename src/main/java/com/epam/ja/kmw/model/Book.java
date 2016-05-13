package com.epam.ja.kmw.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author filipm Provides functions needed to operate on books.
 */
@Entity
@Table(name = "Book")
public class Book {



	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "title")
	private String title;

	@ManyToOne
	@JoinColumn(name = "bookstore_id")
	private BookStore bookStore;

	@Column(name = "date")
	private Date date;

	@Column(name = "author")
	private String author;
	@Column(name = "tags")
	private String tags;

	
	public Book() {
		 
		 
	}

	public Book(String title, BookStore bookStore,  String author, String tags) {

		this.title = title;
		this.bookStore = bookStore;
		this.author = author;
		this.tags = tags;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BookStore getBookStore() {
		return bookStore;
	}

	public void setBookStore(BookStore bookStore) {
		this.bookStore = bookStore;
	}


	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return title+" *** "+author+" *** "+tags;
	}
	

}
