package com.mystica.shishunki.dao;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Article")
public class Article extends ParseObject {

	public Article() {
		// TODO Auto-generated constructor stub
	}
	
	public String getTitle() {
		return getString("Title");
	}
	public void setTitle(String title) {
		put("Title", title);
	}
}
