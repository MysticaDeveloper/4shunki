package com.mystica.shishunki.app.signup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.mystica.shishunki.R;
import com.mystica.shishunki.app.signin.SigninActivity_;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

@EActivity(R.layout.activity_signup)
public class SignupActivity extends FragmentActivity {
	private static final String TAG = SignupActivity.class.toString();

	@ViewById(R.id.editText1)
	protected EditText etId;
	@ViewById(R.id.editText2)
	protected EditText etPassword;
	@ViewById(R.id.editText3)
	protected EditText etEmail;
	@ViewById(R.id.button1)
	protected Button btnLogin;

	@AfterViews
	protected void init() {
		etId.setText("mkawakami0304@gmail.com");
		etPassword.setText("password");
		etEmail.setText("mkawakami0304@gmail.com");
	}

	@Click(R.id.button1)
	protected void signup() {

		ParseUser user = new ParseUser();
		user.setUsername(etId.getText().toString());
		user.setPassword(etPassword.getText().toString());
		user.setEmail(etEmail.getText().toString());

		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					SigninActivity_.intent(SignupActivity.this).start();
				} else {
					Log.e(TAG, "エラーによりサインアップ失敗");
				}
			}
		});
	}
}
