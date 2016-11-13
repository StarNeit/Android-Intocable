package com.intocable;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import com.crashlytics.android.Crashlytics;
import com.intocable.utils.SetupApp;

public class Splash_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Crashlytics.start(this);
		setContentView(R.layout.activity_splash);

		if (SetupApp.checkInternetConnection(Splash_Activity.this)) {
			initmain();
		} else {
			Toast.makeText(this, " Please Connected internet and restart app ",
					Toast.LENGTH_LONG).show();
		}

	}

	private void initmain() {
		// TODO Auto-generated method stub
		Thread BamGio = new Thread() {

			public void run() {
				try {
					sleep(2000);
				} catch (Exception e) {

				} finally {

					Intent newActivity = new Intent(Splash_Activity.this,
							MainHome_Activity.class);
					startActivity(newActivity);
					finish();
				}
			}
		};
		BamGio.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
