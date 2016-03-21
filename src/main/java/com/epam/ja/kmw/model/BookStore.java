package com.epam.ja.kmw.model;

public class BookStore {

	private String url;
	private String tag;
	private String name;
	private String container;
	private String nameTag;
	private String priceTag;
	private String nextTag;

	public BookStore(String name, String url, String tag,String container, String nameTag,String priceTag,String nextTag) {
		this.setName(name);
		this.setUrl(url);
		this.setTag(tag);
		this.setContainer(container);
		this.setNameTag(nameTag);
		this.setPriceTag(priceTag);
		this.setNextTag(nextTag);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
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
