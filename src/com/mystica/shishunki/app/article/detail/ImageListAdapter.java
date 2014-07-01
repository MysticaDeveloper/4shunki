package com.mystica.shishunki.app.article.detail;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mystica.shishunki.R;
import com.mystica.shishunki.dao.Image;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

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
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.gridview_article_image, null);
		}
		Image image = listImage.get(position);
		final ImageView iv = ((ImageView) convertView.findViewById(R.id.imageView1));

		ParseFile fileObject = (ParseFile) image.getImage();
		fileObject.getDataInBackground(new GetDataCallback() {
			public void done(byte[] data, ParseException e) {
				Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
				iv.setImageBitmap(bmp);
			}
		});

		return convertView;
	}

}
