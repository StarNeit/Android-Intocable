package com.intocable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.androidquery.AQuery;
import com.google.android.gcm.GCMRegistrar;
import com.intocable.app_fragments.Fragment_Bio;
import com.intocable.app_fragments.Fragment_Events;
import com.intocable.app_fragments.Fragment_Home;
import com.intocable.app_fragments.Fragment_ListenNow;
import com.intocable.app_fragments.Fragment_Media;
import com.intocable.app_fragments.Fragment_Merchandise;
import com.intocable.app_fragments.Fragment_Music;
import com.intocable.app_fragments.Fragment_News;
import com.intocable.app_fragments.Fragment_Null;
import com.intocable.app_fragments.Fragment_Preference;
import com.intocable.app_fragments.Fragment_WatchNow;
import com.intocable.utils.AppFragmentActivity;
import com.intocable.utils.CommonVariables;
import com.intocable.utils.ConfigApp;
import com.intocable.utils.SetupApp;

/**
 * 
 * @author Micky Main home dung cho framents
 */

public class MainHome_Activity extends AppFragmentActivity {
	public static SharedPreferences sharedPrefs = null;

	private float lastX;

	private LinearLayout bottomBar = null;
	private ViewFlipper viewFlipper = null;
	private ImageView ivMenuPos = null;
	private LinearLayout header;
	private ImageView iv_music = null;
	private ImageView iv_media = null;
	private ImageView iv_news = null;
	private ImageView iv_events = null;
	private ImageView iv_listen_now = null;
	private ImageView iv_merchandise = null;
	private ImageView iv_watch_now = null;
	private ImageView iv_bio = null;
	private ImageView iv_preference = null;

	private LinearLayout llMusic;

	private LinearLayout llMedia;

	private LinearLayout llNews;

	private LinearLayout llEvents;

	private LinearLayout llListen;

	private LinearLayout llMerchanise;

	private LinearLayout llBio;

	private LinearLayout llWatch;

	private LinearLayout llPreferences;
	private static Boolean isLoad = false;
	static int positionMenu = 0;
	static int positionItem = 0;
	static int maxItem = 4;
	private static AQuery aQuery = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GCMIntentService.init(this);
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		GCMRegistrar.register(this, GCMIntentService.SENDER_ID);
		setContentView(R.layout.activity_main_home);
		aQuery = new AQuery(this);
		header = (LinearLayout) findViewById(R.id.header);
		// load ngon ngu duoc chon mac dinh
		sharedPrefs = getSharedPreferences(ConfigApp.SHARED_PREF, MODE_PRIVATE);
		SetupApp.loadShared(sharedPrefs);

		int i_language = sharedPrefs.getInt("LANGUAGE", -1);
		ConfigApp.KEY_LANGUAGE = sharedPrefs.getString("KEY_LANGUAGE", "");
		if (i_language != 1) {
			dialog();
		}
		// neu ngon ngu chua duoc set thi goi giao dien chon ngon ngu
		if (isLoad == false) {
			isLoad = true;
			add_Fragment(new Fragment_Home(), R.id.content_frame);
			if (ConfigApp.KEY_LOCATION == "") {
				// dialog();
			} else
				SetupApp.changeLang(this, ConfigApp.KEY_LOCATION);
		} else {
			set_Fragment(new Fragment_Null(), R.id.content_frame, true);
		}
		initMain();
	}

	public static void showLoading() {
		aQuery.id(R.id.rl_home_loading).visibility(View.VISIBLE);
	}

	public static void hideLoading() {
		aQuery.id(R.id.rl_home_loading).visibility(View.INVISIBLE);
	}

	// cau hinh dialog hien thi khi chua chon ngon ngu mac dinh cho app
	public void dialog() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_language);
		dialog.setCancelable(false);

		final Button btn_english = (Button) dialog
				.findViewById(R.id.btn_english);
		final Button btn_espanol = (Button) dialog
				.findViewById(R.id.btn_espanol);

		btn_english.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ConfigApp.KEY_LANGUAGE = "english/";
				Editor editor = sharedPrefs.edit();
				editor.putInt("LANGUAGE", 1);
				editor.commit();
				btn_espanol.setBackgroundResource(R.drawable.bt_espanol);
				btn_english.setBackgroundResource(R.drawable.bt_english);
				dialog.dismiss();
				SetupApp.changeLang(MainHome_Activity.this, "en");
				SetupApp.saveShared(sharedPrefs);
			}
		});
		btn_espanol.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ConfigApp.KEY_LANGUAGE = "spanish/";
				Editor editor = sharedPrefs.edit();
				editor.putInt("LANGUAGE", 1);
				editor.commit();
				btn_espanol.setBackgroundResource(R.drawable.bt_english);
				btn_english.setBackgroundResource(R.drawable.bt_espanol);
				dialog.dismiss();
				SetupApp.changeLang(MainHome_Activity.this, "es");
				SetupApp.saveShared(sharedPrefs);
			}
		});

		dialog.show();
	}

	// su ly onlick
	@Override
	public void onClick(View v) {
		super.onClick(v);

		int viewID = v.getId();
		Fragment m_fragment = getCurentFragment();
		switch (viewID) {
		case R.id.ll_imv_home:
			if (m_fragment instanceof Fragment_Home)
				break;
			iv_watch_now.setImageResource(R.drawable.ic_btn_watch_now);
			iv_listen_now.setImageResource(R.drawable.ic_btn_listen_now);
			iv_merchandise.setImageResource(R.drawable.ic_btn_merchandise);
			iv_preference.setImageResource(R.drawable.ic_btn_preferences);
			iv_bio.setImageResource(R.drawable.ic_btn_biography);
			iv_events.setImageResource(R.drawable.ic_btn_events);
			iv_news.setImageResource(R.drawable.ic_btn_news);
			iv_media.setImageResource(R.drawable.ic_btn_media);
			iv_music.setImageResource(R.drawable.ic_btn_music);

			set_Fragment(new Fragment_Home(), R.id.content_frame);

			break;
		case R.id.llMusic:
			if (m_fragment instanceof Fragment_Music)
				break;
			positionItem = 1;
			header.setVisibility(View.VISIBLE);
			// SetupApp.setHeader(getString(R.string.header_music), this);
			iv_music.setImageResource(R.drawable.ic_btn_music_on);
			iv_media.setImageResource(R.drawable.ic_btn_media);
			iv_news.setImageResource(R.drawable.ic_btn_news);
			iv_events.setImageResource(R.drawable.ic_btn_events);
			iv_listen_now.setImageResource(R.drawable.ic_btn_listen_now);
			iv_preference.setImageResource(R.drawable.ic_btn_preferences);
			iv_bio.setImageResource(R.drawable.ic_btn_biography);
			iv_watch_now.setImageResource(R.drawable.ic_btn_watch_now);
			iv_merchandise.setImageResource(R.drawable.ic_btn_merchandise);

			set_Fragment(new Fragment_Music(), R.id.content_frame);
			break;
		case R.id.llMedia:
			if (m_fragment instanceof Fragment_Media)
				break;
			positionItem = 2;
			header.setVisibility(View.VISIBLE);
			iv_media.setImageResource(R.drawable.ic_btn_media_on);
			iv_music.setImageResource(R.drawable.ic_btn_music);
			iv_news.setImageResource(R.drawable.ic_btn_news);
			iv_events.setImageResource(R.drawable.ic_btn_events);
			iv_listen_now.setImageResource(R.drawable.ic_btn_listen_now);
			iv_preference.setImageResource(R.drawable.ic_btn_preferences);
			iv_bio.setImageResource(R.drawable.ic_btn_biography);
			iv_watch_now.setImageResource(R.drawable.ic_btn_watch_now);
			iv_merchandise.setImageResource(R.drawable.ic_btn_merchandise);

			set_Fragment(new Fragment_Media(), R.id.content_frame);

			break;
		case R.id.llNews:
			if (m_fragment instanceof Fragment_News)
				break;
			positionItem = 3;
			header.setVisibility(View.VISIBLE);
			iv_news.setImageResource(R.drawable.ic_btn_news_on);
			iv_media.setImageResource(R.drawable.ic_btn_media);
			iv_music.setImageResource(R.drawable.ic_btn_music);
			iv_events.setImageResource(R.drawable.ic_btn_events);
			iv_listen_now.setImageResource(R.drawable.ic_btn_listen_now);
			iv_preference.setImageResource(R.drawable.ic_btn_preferences);
			iv_bio.setImageResource(R.drawable.ic_btn_biography);
			iv_watch_now.setImageResource(R.drawable.ic_btn_watch_now);
			iv_merchandise.setImageResource(R.drawable.ic_btn_merchandise);

			set_Fragment(new Fragment_News(), R.id.content_frame);

			break;
		case R.id.llEvents:
			if (m_fragment instanceof Fragment_Events)
				break;
			positionItem = 4;
			header.setVisibility(View.VISIBLE);
			SetupApp.setHeader(getString(R.string.s_news), this);
			iv_events.setImageResource(R.drawable.ic_btn_events_on);
			iv_news.setImageResource(R.drawable.ic_btn_news);
			iv_media.setImageResource(R.drawable.ic_btn_media);
			iv_music.setImageResource(R.drawable.ic_btn_music);
			iv_listen_now.setImageResource(R.drawable.ic_btn_listen_now);
			iv_preference.setImageResource(R.drawable.ic_btn_preferences);
			iv_bio.setImageResource(R.drawable.ic_btn_biography);
			iv_watch_now.setImageResource(R.drawable.ic_btn_watch_now);
			iv_merchandise.setImageResource(R.drawable.ic_btn_merchandise);
			set_Fragment(new Fragment_Events(), R.id.content_frame);
			break;
		case R.id.llListen:

			if (m_fragment instanceof Fragment_ListenNow)
				break;
			positionItem = 5;
			header.setVisibility(View.VISIBLE);
			iv_listen_now.setImageResource(R.drawable.ic_btn_listen_now_on);
			iv_merchandise.setImageResource(R.drawable.ic_btn_merchandise);
			iv_preference.setImageResource(R.drawable.ic_btn_preferences);
			iv_bio.setImageResource(R.drawable.ic_btn_biography);
			iv_watch_now.setImageResource(R.drawable.ic_btn_watch_now);
			iv_events.setImageResource(R.drawable.ic_btn_events);
			iv_news.setImageResource(R.drawable.ic_btn_news);
			iv_media.setImageResource(R.drawable.ic_btn_media);
			iv_music.setImageResource(R.drawable.ic_btn_music);
			iv_merchandise.setImageResource(R.drawable.ic_btn_merchandise);
			set_Fragment(new Fragment_ListenNow(), R.id.content_frame);
			break;
		case R.id.llMerchanise:
			if (m_fragment instanceof Fragment_Merchandise)
				break;
			positionItem = 6;
			header.setVisibility(View.VISIBLE);

			iv_merchandise.setImageResource(R.drawable.ic_btn_merchandise_on);
			iv_listen_now.setImageResource(R.drawable.ic_btn_listen_now);
			iv_preference.setImageResource(R.drawable.ic_btn_preferences);
			iv_bio.setImageResource(R.drawable.ic_btn_biography);
			iv_watch_now.setImageResource(R.drawable.ic_btn_watch_now);
			iv_events.setImageResource(R.drawable.ic_btn_events);
			iv_news.setImageResource(R.drawable.ic_btn_news);
			iv_media.setImageResource(R.drawable.ic_btn_media);
			iv_music.setImageResource(R.drawable.ic_btn_music);

			set_Fragment(new Fragment_Merchandise(), R.id.content_frame);
			break;
		case R.id.llBio:
			if (m_fragment instanceof Fragment_Bio)
				break;
			positionItem = 7;
			header.setVisibility(View.VISIBLE);
			iv_bio.setImageResource(R.drawable.ic_btn_biography_on);
			iv_listen_now.setImageResource(R.drawable.ic_btn_listen_now);
			iv_merchandise.setImageResource(R.drawable.ic_btn_merchandise);
			iv_preference.setImageResource(R.drawable.ic_btn_preferences);
			iv_watch_now.setImageResource(R.drawable.ic_btn_watch_now);
			iv_events.setImageResource(R.drawable.ic_btn_events);
			iv_news.setImageResource(R.drawable.ic_btn_news);
			iv_media.setImageResource(R.drawable.ic_btn_media);
			iv_music.setImageResource(R.drawable.ic_btn_music);
			set_Fragment(new Fragment_Bio(), R.id.content_frame);
			break;
		case R.id.llWatch:
			if (m_fragment instanceof Fragment_WatchNow)
				break;
			positionItem = 8;
			header.setVisibility(View.VISIBLE);
			iv_watch_now.setImageResource(R.drawable.ic_btn_watch_now_on);
			iv_listen_now.setImageResource(R.drawable.ic_btn_listen_now);
			iv_merchandise.setImageResource(R.drawable.ic_btn_merchandise);
			iv_preference.setImageResource(R.drawable.ic_btn_preferences);
			iv_bio.setImageResource(R.drawable.ic_btn_biography);
			iv_events.setImageResource(R.drawable.ic_btn_events);
			iv_news.setImageResource(R.drawable.ic_btn_news);
			iv_media.setImageResource(R.drawable.ic_btn_media);
			iv_music.setImageResource(R.drawable.ic_btn_music);
			set_Fragment(new Fragment_WatchNow(), R.id.content_frame);
			break;
		case R.id.llPreferences:
			if (m_fragment instanceof Fragment_Preference)
				break;
			positionItem = 9;
			header.setVisibility(View.VISIBLE);
			iv_preference.setImageResource(R.drawable.ic_btn_preferences_on);
			iv_listen_now.setImageResource(R.drawable.ic_btn_listen_now);
			iv_merchandise.setImageResource(R.drawable.ic_btn_merchandise);
			iv_bio.setImageResource(R.drawable.ic_btn_biography);
			iv_watch_now.setImageResource(R.drawable.ic_btn_watch_now);
			iv_events.setImageResource(R.drawable.ic_btn_events);
			iv_news.setImageResource(R.drawable.ic_btn_news);
			iv_media.setImageResource(R.drawable.ic_btn_media);
			iv_music.setImageResource(R.drawable.ic_btn_music);
			set_Fragment(new Fragment_Preference(), R.id.content_frame);
			break;
		case R.id.iv_more:
			break;

		default:
			break;
		}

	}

	protected void initMain() {

		try {
			bottomBar = (LinearLayout) findViewById(R.id.bottomBar);
			viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
			ivMenuPos = (ImageView) findViewById(R.id.iv_more);
			iv_music = (ImageView) findViewById(R.id.iv_music);
			iv_media = (ImageView) findViewById(R.id.iv_media);
			iv_news = (ImageView) findViewById(R.id.iv_news);
			iv_events = (ImageView) findViewById(R.id.iv_events);
			iv_listen_now = (ImageView) findViewById(R.id.iv_listen_now);
			iv_merchandise = (ImageView) findViewById(R.id.iv_merchandise);
			iv_watch_now = (ImageView) findViewById(R.id.iv_watch_now);
			iv_preference = (ImageView) findViewById(R.id.iv_preferences);
			iv_bio = (ImageView) findViewById(R.id.iv_bio);

			llMusic = (LinearLayout) findViewById(R.id.llMusic);
			llMedia = (LinearLayout) findViewById(R.id.llMedia);
			llNews = (LinearLayout) findViewById(R.id.llNews);
			llEvents = (LinearLayout) findViewById(R.id.llEvents);
			llListen = (LinearLayout) findViewById(R.id.llListen);
			llMerchanise = (LinearLayout) findViewById(R.id.llMerchanise);
			llBio = (LinearLayout) findViewById(R.id.llBio);
			llWatch = (LinearLayout) findViewById(R.id.llWatch);
			llPreferences = (LinearLayout) findViewById(R.id.llPreferences);

			// set lai item dang duoc chon khi rotation

			iv_watch_now.setImageResource(R.drawable.ic_btn_watch_now);
			iv_listen_now.setImageResource(R.drawable.ic_btn_listen_now);
			iv_merchandise.setImageResource(R.drawable.ic_btn_merchandise);
			iv_preference.setImageResource(R.drawable.ic_btn_preferences);
			iv_bio.setImageResource(R.drawable.ic_btn_biography);
			iv_events.setImageResource(R.drawable.ic_btn_events);
			iv_news.setImageResource(R.drawable.ic_btn_news);
			iv_media.setImageResource(R.drawable.ic_btn_media);
			iv_music.setImageResource(R.drawable.ic_btn_music);
			if (positionItem == 1)
				iv_music.setImageResource(R.drawable.ic_btn_music_on);
			else if (positionItem == 2)
				iv_media.setImageResource(R.drawable.ic_btn_media_on);
			else if (positionItem == 3)
				iv_news.setImageResource(R.drawable.ic_btn_news_on);
			else if (positionItem == 4)
				iv_events.setImageResource(R.drawable.ic_btn_events_on);
			else if (positionItem == 5)
				iv_listen_now.setImageResource(R.drawable.ic_btn_listen_now_on);
			else if (positionItem == 6)
				iv_merchandise
						.setImageResource(R.drawable.ic_btn_merchandise_on);
			else if (positionItem == 7)
				iv_bio.setImageResource(R.drawable.ic_btn_biography_on);
			else if (positionItem == 8)
				iv_watch_now.setImageResource(R.drawable.ic_btn_watch_now_on);
			else if (positionItem == 9)
				iv_preference
						.setImageResource(R.drawable.ic_btn_preferences_on);

			Display getOrient = getWindowManager().getDefaultDisplay();
			if (getOrient.getWidth() == getOrient.getHeight()) {
				maxItem = 4;
			} else {
				if (getOrient.getWidth() < getOrient.getHeight()) {
					maxItem = 4;

				} else {
					maxItem = 5;
				}
			}

		} catch (Exception e) {
			bottomBar = null;
			viewFlipper = null;
			ivMenuPos = null;
		}

		if (viewFlipper != null && ivMenuPos != null) {
			int orientation = getResources().getConfiguration().orientation;

			positionMenu = (positionItem > 0 ? positionItem - 1 : 0) / maxItem;
			viewFlipper.setDisplayedChild(positionMenu);
			switch (viewFlipper.getDisplayedChild()) {
			case 0:
				ivMenuPos.setImageResource(R.drawable.tab1);
				break;
			case 1:
				ivMenuPos.setImageResource(R.drawable.tab2);
				break;
			case 2:
				ivMenuPos.setImageResource(R.drawable.tab3);
				break;
			}
			hideLoading();
		}

		OnTouchListener onTouchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent touchevent) {
				int viewID = v.getId();

				// TODO Auto-generated method stub
				switch (touchevent.getAction()) {
				// when user first touches the screen to swap
				case MotionEvent.ACTION_DOWN: {
					if ((viewID == R.id.llMusic) || (viewID == R.id.llMedia)
							|| (viewID == R.id.llNews)
							|| (viewID == R.id.llEvents)
							|| (viewID == R.id.llListen)
							|| (viewID == R.id.llMerchanise)
							|| (viewID == R.id.llBio)
							|| (viewID == R.id.llWatch)
							|| (viewID == R.id.llPreferences)

					)
						onTouch(bottomBar, touchevent);
					else
						lastX = touchevent.getX();
					return true;
				}
				case MotionEvent.ACTION_UP: {
					float currentX = touchevent.getX();

					if (((viewID == R.id.llMusic) || (viewID == R.id.llMedia)
							|| (viewID == R.id.llNews)
							|| (viewID == R.id.llEvents)
							|| (viewID == R.id.llListen)
							|| (viewID == R.id.llMerchanise)
							|| (viewID == R.id.llBio)
							|| (viewID == R.id.llWatch) || (viewID == R.id.llPreferences)

					)
							&& Math.abs(lastX - currentX) < 10) {
						onClick(v);
						return false;

					}

					// if left to right swipe on screen
					if (lastX < currentX) {
						// If no more View/Child to flip
						if (viewFlipper.getDisplayedChild() == 0)
							break;

						// set the required Animation type to ViewFlipper
						// The Next screen will come in form Left and
						// current Screen
						// will go OUT from Right
						viewFlipper.setInAnimation(getApplicationContext(),
								R.anim.in_from_left);
						viewFlipper.setOutAnimation(getApplicationContext(),
								R.anim.out_to_right);
						// Show the next Screen
						viewFlipper.showPrevious();

					}

					// if right to left swipe on screen
					if (lastX > currentX) {
						if (viewFlipper.getDisplayedChild() == viewFlipper
								.getChildCount() - 1)
							break;
						// set the required Animation type to ViewFlipper
						// The Next screen will come in form Right and
						// current Screen
						// will go OUT from Left
						viewFlipper.setInAnimation(getApplicationContext(),
								R.anim.in_from_right);
						viewFlipper.setOutAnimation(getApplicationContext(),
								R.anim.out_to_left);
						// Show The Previous Screen
						viewFlipper.showNext();
					}

					switch (viewFlipper.getDisplayedChild()) {
					case 0:
						ivMenuPos.setImageResource(R.drawable.tab1);
						break;
					case 1:
						ivMenuPos.setImageResource(R.drawable.tab2);
						break;
					case 2:
						ivMenuPos.setImageResource(R.drawable.tab3);
						break;
					}
					positionMenu = viewFlipper.getDisplayedChild();
					return true;
				}
				}
				return false;
			}
		};

		if (bottomBar != null) {
			llMusic.setOnTouchListener(onTouchListener);
			llMedia.setOnTouchListener(onTouchListener);
			llNews.setOnTouchListener(onTouchListener);
			llEvents.setOnTouchListener(onTouchListener);
			llListen.setOnTouchListener(onTouchListener);
			llMerchanise.setOnTouchListener(onTouchListener);
			llBio.setOnTouchListener(onTouchListener);
			llWatch.setOnTouchListener(onTouchListener);
			llPreferences.setOnTouchListener(onTouchListener);

			bottomBar.setOnTouchListener(onTouchListener);

		}
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
								isLoad = false;
								positionMenu = 0;
								positionItem = 0;
								maxItem = 4;

								CommonVariables.resetData();

								MainHome_Activity.this.finish();

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

	@Override
	public void onBackPressed() {
		Fragment m_fragment = getCurentFragment();
		if ((m_fragment instanceof Fragment_Home)
				|| (m_fragment instanceof Fragment_Music)
				|| (m_fragment instanceof Fragment_Media)
				|| (m_fragment instanceof Fragment_News)
				|| (m_fragment instanceof Fragment_Events)
				|| (m_fragment instanceof Fragment_ListenNow)
				|| (m_fragment instanceof Fragment_Merchandise)
				|| (m_fragment instanceof Fragment_Bio)
				|| (m_fragment instanceof Fragment_WatchNow)
				|| (m_fragment instanceof Fragment_Preference)) {
			checkExitApp();
			return;
		} else
			super.onBackPressed();
	}

	private void restartActivity() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			maxItem = 4;
		} else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			maxItem = 5;
		}

		if (SetupApp.is_changed_myLocale == true) {
			setContentView(R.layout.activity_main_home);
			set_Fragment(new Fragment_Null(), R.id.content_frame, true);
			initMain();
			super.onConfigurationChanged(newConfig);
		}

	}
}
