package com.mystica.shishunki.app.article.imageassessment;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageButton;

import com.mystica.shishunki.R;
import com.mystica.shishunki.dao.Article;
import com.mystica.shishunki.dao.Image;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

@EActivity(R.layout.activity_article_image_assessment)
public class ArticleImageAssessmentActivity extends FragmentActivity {

	@Extra
	Article article;

	@ViewById(R.id.imageButton1)
	protected ImageButton imImage1;
	@ViewById(R.id.imageButton2)
	protected ImageButton imImage2;

	protected Image image1;
	protected Image image2;

	@AfterViews
	protected void init() {
	}

	@Click(R.id.imageButton1)
	public void clickImage1() {
		parse(image1, image2);
	}

	@Click(R.id.imageButton2)
	public void clickImage2() {
		parse(image2, image1);
	}

	protected void parse(Image win, Image lose) {
		// FIXME:Parseへ評価を登録する。
	}
}
