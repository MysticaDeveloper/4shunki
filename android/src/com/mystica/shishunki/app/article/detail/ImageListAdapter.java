package com.mystica.shishunki.app.article.detail;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mystica.shishunki.R;
import com.mystica.shishunki.dao.Image;

public class ImageListAdapter extends BaseAdapter {

	private Activity activity;
	private List<Image> listImage;

	public ImageListAdapter(Activity activity, List<Image> listImage) {
		super();
		this.activity = activity;
		this.listImage = listImage;
		Log.d("", listImage.size()+"件");
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
		new DownloadTask(iv).execute(image.url);
		return convertView;
	}
	
	private Drawable ImageOperations(Context ctx, String url, String saveFilename) {
		try {
			InputStream is = (InputStream) this.fetch(url);
			Drawable d = Drawable.createFromStream(is, "src");
			return d;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object fetch(String address) throws MalformedURLException,IOException {
		URL url = new URL(address);
		Object content = url.getContent();
		return content;
	}
	class DownloadTask extends AsyncTask<String, Integer, Integer> {
	    private ImageView imageView;
	    private Drawable drawable;
	 
	    public DownloadTask(ImageView imageView) {
	        this.imageView = imageView;
	    }
	 
	    @Override
	    protected Integer doInBackground(String... urls) {
        	drawable = ImageOperations(activity,urls[0],"image.jpg");
			return 1;
	    }
	 
	    protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			Log.d("", "start #onPostExecute" );
	    	
            if (drawable != null) {
                this.imageView.setImageDrawable(drawable);
                this.imageView.setVisibility(View.VISIBLE);
            } else {
                this.imageView.setVisibility(View.INVISIBLE);
            }
	    }
	}
}
