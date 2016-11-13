package com.intocable.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.intocable.R;

public class BaseActivity extends YouTubeBaseActivity {

	static BaseActivity baseActivity = null;

	static int positionMenu = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public static BaseActivity getActivity() {
		return baseActivity;
	}

	public void onClick(View v) {

	}

	public void checkExitApp() {
		new AlertDialog.Builder(this)
				.setTitle(getResources().getString(R.string.message))
				.setMessage(getResources().getString(R.string.exit_app))
				.setPositiveButton(getResources().getString(R.string.yes),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								BaseActivity.this.finish();
							}
						})
				.setNegativeButton(getResources().getString(R.string.no),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}).create().show();
	}

}
