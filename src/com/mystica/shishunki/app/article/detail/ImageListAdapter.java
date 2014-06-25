package com.mystica.shishunki.app.article.detail;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mystica.shishunki.dao.Image;

public class ImageListAdapter extends BaseAdapter {

	private List<Image> listImage;
	public ImageListAdapter(List<Image> listImage) {
		this.listImage = listImage;
	}

	@Override
	public int getCount() {
		return listImage.size();
	}

	@Override
	public Object getItem(int position) {
		return listImage.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}
