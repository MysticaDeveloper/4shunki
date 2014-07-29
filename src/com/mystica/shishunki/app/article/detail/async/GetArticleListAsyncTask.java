package com.mystica.shishunki.app.article.detail.async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.mystica.shishunki.R;
import com.mystica.shishunki.api.Request;
import com.mystica.shishunki.app.article.detail.ArticleDetailActivity;
import com.mystica.shishunki.app.article.detail.ImageListAdapter;
import com.mystica.shishunki.dao.Image;

public class GetArticleListAsyncTask extends AsyncTask<Map, Integer, Integer> {

	private ArticleDetailActivity activity;
	private JSONArray json_array_results;
	private List<Image> listImage;
	public GetArticleListAsyncTask(ArticleDetailActivity articleDetailActivity) {
		activity = articleDetailActivity;
	}

	@Override
	protected Integer doInBackground(Map... params) {
		searchByBingAPI("おっぱい");
		return 1;
	}
	
	static final String BING_SERARCH_API_KEY = "BZKq/QZsTJpXcOxXf69o5Tdek8U2f9gVKv9qAk27Rdg";

	public void searchByBingAPI(String keywords) {
		try {
			keywords = Uri.encode(keywords);
			String url = "http://image-search-garnet.herokuapp.com/"+keywords+".json";

			listImage = new ArrayList();
			String line = Request.get(url);

			if (line != null) {
				json_array_results = new JSONArray(line);
				for (int i = 0; i < json_array_results.length(); i++) {
					Image image = new Image();
					image.url = json_array_results.getString(i);
					listImage.add(image);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		ListView imageView = (ListView)activity.findViewById(R.id.gridViewImage);
		imageView.setAdapter(new ImageListAdapter(activity, listImage));
	}
}
