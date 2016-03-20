package com.epam.ja.kmw.model;

import scraping.Searchable;

public class BookStore {
	
	private String url;
	private String tag;
	private String name;
	public BookStore(String name,String url, String tag){
		this.setName(name);
		this.setUrl(url);
		this.setTag(tag);
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
	

}
