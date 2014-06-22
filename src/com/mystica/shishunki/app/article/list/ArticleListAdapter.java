package com.mystica.shishunki.app.article.list;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mystica.shishunki.R;
import com.mystica.shishunki.dao.Article;

public class ArticleListAdapter extends BaseAdapter {

	private List<Article> listArticle;
	public ArticleListAdapter(List<Article> instruments) {
		listArticle = instruments;
	}

	@Override
	public int getCount() {
		return listArticle.size();
	}

	@Override
	public Article getItem(int position) {
		return listArticle.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listview_article_list, null);
		}
		((TextView)convertView.findViewById(R.id.textView1)).setText(getItem(position).getTitle());
		return convertView;
	}

}
