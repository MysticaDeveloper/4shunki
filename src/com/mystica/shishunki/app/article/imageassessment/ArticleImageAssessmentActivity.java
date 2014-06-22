package com.mystica.shishunki.app.article.imageassessment;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageButton;

import com.mystica.shishunki.R;
import com.mystica.shishunki.dao.Image;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

@EActivity(R.layout.activity_article_image_assessment)
public class ArticleImageAssessmentActivity extends FragmentActivity {

	@ViewById(R.id.imageButton1)
	protected ImageButton imImage1;
	@ViewById(R.id.imageButton2)
	protected ImageButton imImage2;

	@AfterViews
	protected void init() {
		ParseQuery<Image> woodwinds = ParseQuery.getQuery(Image.class);
		woodwinds.findInBackground(new FindCallback<Image>() {
			public void done(List<Image> image, ParseException exception) {
				{
				     ParseFile fileObject = (ParseFile) image.get(0).getImage();
		             fileObject.getDataInBackground(new GetDataCallback() {
	                     public void done(byte[] data, ParseException e) {
			                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
							imImage1.setImageBitmap(bmp);
		                }
					});
				}
				{
				     ParseFile fileObject = (ParseFile) image.get(1).getImage();
		             fileObject.getDataInBackground(new GetDataCallback() {
	                     public void done(byte[] data, ParseException e) {
			                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
							imImage2.setImageBitmap(bmp);
		                }
					});
				}
			}
		});
	}
}
