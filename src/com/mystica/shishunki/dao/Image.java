package com.mystica.shishunki.dao;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Image")
public class Image extends ParseObject {

	public Image() {
		// TODO Auto-generated constructor stub
	}
	
	public ParseFile getImage() {
		return getParseFile("Image");
	}
}
