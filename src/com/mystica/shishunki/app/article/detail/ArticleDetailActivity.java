package com.mystica.shishunki.app.article.detail;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.GridView;

import com.mystica.shishunki.R;
import com.mystica.shishunki.app.article.imageassessment.ArticleImageAssessmentActivity_;
import com.mystica.shishunki.dao.Article;
import com.mystica.shishunki.dao.Image;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

@EActivity(R.layout.activity_article_detail)
public class ArticleDetailActivity extends FragmentActivity {
	@Extra
	Article article;

	@ViewById(R.id.gridView1)
	protected GridView gridViewImage;

	@AfterViews
	protected void init() {
		ParseQuery innerQuery = new ParseQuery(Article.class);
		innerQuery.whereEqualTo("objectId", article.getObjectId());

		ParseQuery<Image> woodwinds = ParseQuery.getQuery(Image.class);
		woodwinds.whereMatchesQuery("Article", innerQuery);
		woodwinds.findInBackground(new FindCallback<Image>() {
			public void done(List<Image> listImage, ParseException exception) {
				Log.d("", listImage.size()+"");
				gridViewImage.setAdapter(new ImageListAdapter(listImage));
			}
		});
	}

	@Click(R.id.button1)
	protected void openAssessment() {
		ArticleImageAssessmentActivity_.intent(this).article(article).start();
	}
}
