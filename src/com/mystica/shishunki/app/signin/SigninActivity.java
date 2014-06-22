package com.mystica.shishunki.app.signin;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.EditText;

import com.mystica.shishunki.R;
import com.mystica.shishunki.app.article.list.ArticleListActivity_;
import com.mystica.shishunki.app.signup.SignupActivity_;
import com.mystica.shishunki.dao.Article;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

@EActivity(R.layout.activity_signin)
public class SigninActivity extends FragmentActivity {
	private static final String TAG = SigninActivity.class.toString();

	@ViewById(R.id.editText1)
	protected EditText etId;
	@ViewById(R.id.editText2)
	protected EditText etPassword;

	@AfterViews
	protected void init() {
		Parse.initialize(this, "tb9z4W5lhHUuzwyyPTNGALb69KMuQEhrQQbBnxv7", "9azTcFYxpiIWC3vKVHGIoHedHOlWe1svF2zQf9Ob");
		ParseObject.registerSubclass(Article.class);
		
		etId.setText("mkawakami0304@gmail.com");
		etPassword.setText("password");
	}

	@Click(R.id.button1)
	protected void login() {
		ParseUser.logInInBackground(etId.getText().toString(), etPassword.getText().toString(), new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					Log.d(TAG, "ログイン成功");
					ArticleListActivity_.intent(SigninActivity.this).start();
				} else {
					Log.e(TAG, "エラーによりログイン失敗");
				}
			}
		});
	}

	@Click(R.id.button2)
	protected void signup() {
		SignupActivity_.intent(this).start();
	}
}
