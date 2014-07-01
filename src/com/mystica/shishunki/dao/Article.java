package com.mystica.shishunki.dao;

import java.io.Serializable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Article")
public class Article extends ParseObject implements Serializable {

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
