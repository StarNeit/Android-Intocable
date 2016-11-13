package com.intocable.app_fragments;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
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

public class Fragment_VideoView extends AppFragment implements
		OnInitializedListener {
	private static String API_KEY = "AIzaSyDPLcH8Wc-skTgndlt-xaTdYJa3Hye7ols";
	private static String YOUTUBE_PACKAGE = "com.google.android.youtube";

	public static String url_play;
	public static String name_video;

	private String video_id;
	private TextView tv_title_video;
	private WebView webview;
	private LinearLayout youtube;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());

		View view = inflater.inflate(R.layout.fragment_view_video, null);
		SetupApp.setHeader(getString(R.string.s_video), getActivity());
		initmain(view);
		initData();
		return view;
	}

	private void initmain(View view) {
		tv_title_video = (TextView) view.findViewById(R.id.tv_title_video);
		webview = (WebView) view.findViewById(R.id.webview);
		youtube = (LinearLayout) view.findViewById(R.id.youtube);
	}

	private void initData() {
		// TODO Auto-generated method stub

		tv_title_video.setText(name_video);

		// check youtube app or link have youtube
		if (!SetupApp.isAppInstalled(getActivity(), YOUTUBE_PACKAGE)
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
					progDailog = ProgressDialog.show(getActivity(), "",
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

		YouTubePlayerSupportFragment youTubePlayerView = (YouTubePlayerSupportFragment) getActivity()
				.getSupportFragmentManager().findFragmentById(
						R.id.youtube_player);
		youTubePlayerView.initialize(API_KEY, this);
		new YouTubePageStreamUriGetter(getActivity()).execute(url_play);
	}

	@Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult result) {
		Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onInitializationSuccess(Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		// player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
		/** add listeners to YouTubePlayer instance **/

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
			Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_SHORT)
					.show();
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

	private void destroyFragment() {
		getChildFragmentManager().beginTransaction().hide(this).commit();
	}

	@Override
	public void onBackPressed() {
		if (webview.getVisibility() == View.VISIBLE) {
			webview.removeAllViews();
			webview.destroy();
		}
		Log.d("Debug Lan", " cai chi day");
		destroyFragment();
		onDestroyView();
		super.onBackPressed();
	}

	@Override
	public void onStop() {
		if (webview.getVisibility() == View.VISIBLE) {
			webview.removeAllViews();
			webview.destroy();
		}
		// destroyFragment();
		finish();
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		YouTubePlayerFragment youTubePlayerView = (YouTubePlayerFragment) getActivity()
				.getFragmentManager().findFragmentById(R.id.youtube_player);
		if (youTubePlayerView != null)
			getFragmentManager().beginTransaction().remove(this).commit();

	}
}
