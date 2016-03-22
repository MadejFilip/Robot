package com.epam.ja.kmw.model;

public class BookStore {

	private int id;
	private String name;
	private String url;
	private String nameTag;
	private String priceTag;
	private String nextTag;


	public BookStore(String name, String url, String nameTag,String priceTag,String nextTag) {
		this.setName(name);
		this.setUrl(url);
		this.setNameTag(nameTag);
		this.setPriceTag(priceTag);
		this.setNextTag(nextTag);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

}
