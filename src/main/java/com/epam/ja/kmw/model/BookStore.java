package com.epam.ja.kmw.model;

public class BookStore {

	private int id;
	private String name;
	private String url;
	private String nameTag;
	private String priceTag;
	private String nextTag;
	private String priceValue;
	private String authorTag ;
	private String tagsTag ;
	private String type ;


	public BookStore(String name, String url, String nameTag, String priceTag, String nextTag, String priceValue, String authorTag,String tagsTag, String type) {
		this.setType(type);
		this.setTagsTag(tagsTag);
		this.setAuthorTag(authorTag);
		this.setName(name);
		this.setUrl(url);
		this.setNameTag(nameTag);
		this.setPriceTag(priceTag);
		this.setNextTag(nextTag);
		this.setPriceValue(priceValue);
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
