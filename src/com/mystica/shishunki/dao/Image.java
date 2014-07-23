package com.mystica.shishunki.dao;

import org.json.JSONException;
import org.json.JSONObject;

public class Image {

	public String url;
	public Image() {
	}
	
	public static Image createEntity(JSONObject jsonObject) {
		Image image = new Image();
		try {
			image.url = jsonObject.getString("MediaUrl");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return image;
	}
}
