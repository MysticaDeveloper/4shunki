package com.mystica.shishunki.app.article.list;

import java.util.List;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.mystica.shishunki.R;
import com.mystica.shishunki.dao.Article;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

@EActivity(R.layout.activity_article_list)
public class ArticleListActivity extends FragmentActivity {

	@ViewById(R.id.listView1)
	protected ListView listViewArticle;

	@AfterViews
	protected void init() {
		ParseQuery<Article> woodwinds = ParseQuery.getQuery(Article.class);
		woodwinds.findInBackground(new FindCallback<Article>() {
			public void done(List<Article> instruments, ParseException exception) {
				listViewArticle.setAdapter(new ArticleListAdapter(instruments));
			}
		});
	}
}
