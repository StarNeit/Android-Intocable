package com.intocable.app_fragments;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.utils.AppFragment;
import com.intocable.utils.ConfigApp;
import com.intocable.utils.SetupApp;
import com.intocable.utils.YouTubePageStreamUriGetter;

public class Youtube_video_Activity extends YouTubeBaseActivity implements
		OnInitializedListener, YouTubePlayer.OnFullscreenListener,

		YouTubePlayer.PlayerStateChangeListener {
	private static String API_KEY = "AIzaSyDPLcH8Wc-skTgndlt-xaTdYJa3Hye7ols";
	private static String YOUTUBE_PACKAGE = "com.google.android.youtube";

	public static String url_play;
	public static String name_video;

	private String video_id;
	private TextView tv_title_video;
	private WebView webview;
	private LinearLayout youtube;
	private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9 ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
			: ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

	private static final int LANDSCAPE_ORIENTATION = Build.VERSION.SDK_INT < 9 ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
			: ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;

	private YouTubePlayer mPlayer = null;
	private boolean mAutoRotation = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAutoRotation = Settings.System.getInt(getContentResolver(),
				Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
		setContentView(R.layout.activity_view_video_youtube1);
		initmain();
		initData();
	}

	private void initmain() {
		tv_title_video = (TextView) findViewById(R.id.tv_title_videos);
		webview = (WebView) findViewById(R.id.webview);
		youtube = (LinearLayout) findViewById(R.id.youtube);
	}

	private void initData() {
		// TODO Auto-generated method stub

		tv_title_video.setText(name_video);
		if (!SetupApp.isAppInstalled(this, YOUTUBE_PACKAGE)
				|| !SetupApp.checkLinkYoutube(url_play)) {
			youtube.setVisibility(View.GONE);
			webview.setVisibility(View.VISIBLE);
			setupWeb();
		} else {
			youtube.setVisibility(View.VISIBLE);
			webview.setVisibility(View.GONE);
			setupYoutubePlayer();
		}
	}

	private void setupWeb() {
		WebSettings webSettings = webview.getSettings();
		webSettings.setAppCacheEnabled(true);
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setJavaScriptEnabled(true);
		webSettings.setPluginState(PluginState.ON);
		webSettings.setAllowFileAccess(true);
		webSettings
				.setUserAgentString("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/534.36 (KHTML, like Gecko) Chrome/13.0.766.0 Safari/534.36");

		webview.setWebChromeClient(new WebChromeClient());
		webview.setWebViewClient(new WebViewClient() {

			private Dialog progDailog = null;

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (progDailog == null)
					progDailog = ProgressDialog.show(
							Youtube_video_Activity.this, "",
							"Loading. Please wait...", false);
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				if (progDailog != null) {
					progDailog.dismiss();
					progDailog = null;
				}
				// view.setInitialScale(50);
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				progDailog.dismiss();
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

		});

		// create embeded link
		if (SetupApp.checkLinkYoutube(url_play)) {
			video_id = getYoutubeId();
			String url = "http://www.youtube.com/embed/" + video_id;
			webview.loadUrl(url);
		} else
			webview.loadUrl(url_play);
	}

	private void setupYoutubePlayer() {
		video_id = getYoutubeId();

		YouTubePlayerFragment youTubePlayerView = (YouTubePlayerFragment) getFragmentManager()
				.findFragmentById(R.id.youtube_player);
		youTubePlayerView.initialize(API_KEY, this);
		new YouTubePageStreamUriGetter(this).execute(video_id);
	}

	@Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult result) {
		Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onInitializationSuccess(Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
		/** add listeners to YouTubePlayer instance **/
		mPlayer = player;
		player.setPlayerStateChangeListener(this);
		player.setOnFullscreenListener(this);
		if (mAutoRotation) {
			player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
					| YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
					| YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE
					| YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
		} else {
			player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
					| YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
					| YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
		}
		player.setPlayerStateChangeListener(playerStateChangeListener);
		player.setPlaybackEventListener(playbackEventListener);

		/** Start buffering **/
		if (!wasRestored) {
			player.cueVideo(video_id);
		}
	}

	private PlaybackEventListener playbackEventListener = new PlaybackEventListener() {

		@Override
		public void onBuffering(boolean arg0) {

		}

		@Override
		public void onPaused() {

		}

		@Override
		public void onPlaying() {

		}

		@Override
		public void onSeekTo(int arg0) {

		}

		@Override
		public void onStopped() {

		}

	};

	private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {

		@Override
		public void onAdStarted() {

		}

		@Override
		public void onError(ErrorReason result) {
			Toast.makeText(Youtube_video_Activity.this, result.toString(),
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onLoaded(String arg0) {

		}

		@Override
		public void onLoading() {
		}

		@Override
		public void onVideoEnded() {

		}

		@Override
		public void onVideoStarted() {

		}
	};

	private String getYoutubeId() {
		String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu\\.be\\/)[^#\\&\\?]*";

		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(url_play);

		if (matcher.find()) {
			return matcher.group();
		}
		return "";
	}

	@Override
	public void onBackPressed() {
		if (webview.getVisibility() == View.VISIBLE) {
			webview.removeAllViews();
			webview.destroy();
		}
		Log.d("Debug Lan", " cai chi day");
		super.onBackPressed();
	}

	@Override
	public void onStop() {
		if (webview.getVisibility() == View.VISIBLE) {
			webview.removeAllViews();
			webview.destroy();
		}
		// destroyFragment();
		// finish();
		super.onStop();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			if (mPlayer != null)
				mPlayer.setFullscreen(true);
		}
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			if (mPlayer != null)
				mPlayer.setFullscreen(false);
		}
	}

	public void onFullscreen(boolean fullsize) {
		if (fullsize) {
			setRequestedOrientation(LANDSCAPE_ORIENTATION);
		} else {
			setRequestedOrientation(PORTRAIT_ORIENTATION);
		}
	}

	@Override
	public void onAdStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(ErrorReason arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoaded(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoading() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onVideoEnded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onVideoStarted() {
		// TODO Auto-generated method stub

	}
}
