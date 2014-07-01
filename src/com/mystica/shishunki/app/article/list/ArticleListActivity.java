package com.mystica.shishunki.app.article.list;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mystica.shishunki.R;
import com.mystica.shishunki.app.article.detail.ArticleDetailActivity_;
import com.mystica.shishunki.dao.Article;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

@EActivity(R.layout.activity_article_list)
public class ArticleListActivity extends FragmentActivity {

	private AdView adView;

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

		/**
		 * AdMob表示
		 */
		adView = new AdView(this);
		adView.setAdUnitId(getResources().getString(R.string.admob_unit_id));
//		adView.setAdSize(AdSize.BANNER);
		adView.setAdSize(AdSize.SMART_BANNER);

		LinearLayout layout_ad_footer = (LinearLayout) findViewById(R.id.layout_ad_footer);
		layout_ad_footer.addView(adView);

		AdRequest adRequest = new AdRequest.Builder()
				// all emulators
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice(getResources().getString(R.string.debug_device_id_c1905))
				.build();

		adView.loadAd(adRequest);
	}

	@ItemClick(R.id.listView1)
	protected void openDetail(Article article) {
		ArticleDetailActivity_.intent(this).article(article).start();
	}

	@Override
	protected void onResume() {
		super.onResume();
		adView.resume();
	}

	@Override
	protected void onPause() {
		adView.pause();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		adView.destroy();
		super.onDestroy();
	}
}
