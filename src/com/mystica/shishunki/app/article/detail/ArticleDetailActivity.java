package com.mystica.shishunki.app.article.detail;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import android.support.v4.app.FragmentActivity;

import com.mystica.shishunki.R;
import com.mystica.shishunki.app.article.imageassessment.ArticleImageAssessmentActivity_;

@EActivity(R.layout.activity_article_detail)
public class ArticleDetailActivity extends FragmentActivity {

	@Click(R.id.button1)
	protected void openAssessment() {
		ArticleImageAssessmentActivity_.intent(this).start();
	}
}
