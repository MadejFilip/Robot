package com.epam.ja.kmw.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BookStore")
public class BookStore {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bookstore_id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "url")
	private String url;
	@Column(name = "nameTag")
	private String nameTag;
	@Column(name = "priceTag")
	private String priceTag;
	@Column(name = "nextTag")
	private String nextTag;
	@Column(name = "priceValue")
	private String priceValue;
	@Column(name = "authorTag")
	private String authorTag;
	@Column(name = "tagsTag")
	private String tagsTag;
	@Column(name = "type")
	private String type;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<Book> book;

	public Set<Book> getBook() {
		return book;
	}

	public void setBook(Set<Book> book) {
		this.book = book;
	}

	public BookStore() {
	}

	public Integer getId() {
		return id;
	}

	public BookStore(String name, String url, String nameTag, String priceTag, String nextTag, String priceValue,
			String authorTag, String tagsTag, String type) {
		this.name = name;
		this.url = url;
		this.nameTag = nameTag;
		this.priceTag = priceTag;
		this.nextTag = nextTag;
		this.priceValue = priceValue;
		this.authorTag = authorTag;
		this.tagsTag = tagsTag;
		this.type = type;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameTag() {
		return nameTag;
	}

	public void setNameTag(String nameTag) {
		this.nameTag = nameTag;
	}

	public String getPriceTag() {
		return priceTag;
	}

	public void setPriceTag(String priceTag) {
		this.priceTag = priceTag;
	}

	public String getNextTag() {
		return nextTag;
	}

	public void setNextTag(String nextTag) {
		this.nextTag = nextTag;
	}

	public String getPriceValue() {
		return priceValue;
	}

	public void setPriceValue(String priceValue) {
		this.priceValue = priceValue;
	}

	public String getAuthorTag() {
		return authorTag;
	}

	public void setAuthorTag(String authorTag) {
		this.authorTag = authorTag;
	}

	public String getTagsTag() {
		return tagsTag;
	}

	public void setTagsTag(String tagsTag) {
		this.tagsTag = tagsTag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
