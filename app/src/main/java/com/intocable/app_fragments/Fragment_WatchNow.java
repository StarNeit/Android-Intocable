package com.intocable.app_fragments;

import java.io.IOException;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.utils.AppFragment;
import com.intocable.utils.SetupApp;
import com.intocable.utils.XMLParserHelper;
import com.intocable.ws.GetXmlWeb;
import com.intocable.ws.OnResultAsyncTask;

public class Fragment_WatchNow extends AppFragment implements OnResultAsyncTask {
	private String link = "http://new.livestream.com/accounts/2212101/events/3585268/player?width=560&height=315&autoPlay=true&mute=false";
	private TextView tv_watch;

	private WebView webView;
	private AudioManager audioManager = null;
	private SeekBar seekbar;
	private AQuery aQuery;
	private TextView tv_msg_streamlive;
	public static GetXmlWeb xmlGetter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		aQuery = new AQuery(getActivity());
		View view = inflater.inflate(R.layout.fragment_watch_now_offline, null);
		SetupApp.setHeader(getString(R.string.s_watch), getActivity());

		initmain(view);
		getData();

		return view;
	}

	private void getData() {
		MainHome_Activity.showLoading();
		if (xmlGetter != null && !xmlGetter.isCancelled())
			xmlGetter.cancel(true);

		xmlGetter = new GetXmlWeb(this, GetXmlWeb.FOREGROUND,
				GetXmlWeb.TYPE_LIVESTREAM);
		xmlGetter.execute();
	}

	private void initmain(View view) {
		tv_watch = (TextView) view.findViewById(R.id.tv_watch);

		webView = (WebView) view.findViewById(R.id.webView);
		seekbar = (SeekBar) view.findViewById(R.id.seekBar_watch_now);
		tv_msg_streamlive = (TextView) view
				.findViewById(R.id.tv_msg_streamlive);
		webView.setVisibility(View.GONE);
		tv_msg_streamlive.setVisibility(View.GONE);

	}

	private void initControls() {
		// TODO Auto-generated method stub
		try {
			audioManager = (AudioManager) getActivity().getSystemService(
					Context.AUDIO_SERVICE);
			seekbar.setMax(audioManager
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
			seekbar.setProgress(audioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC));

			seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				@Override
				public void onStopTrackingTouch(SeekBar arg0) {
				}

				@Override
				public void onStartTrackingTouch(SeekBar arg0) {
				}

				@Override
				public void onProgressChanged(SeekBar arg0, int progress,
						boolean arg2) {
					audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
							progress, 0);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initData() {
		// TODO Auto-generated method stub

		webView.setWebViewClient(new WebViewClient() {
			ProgressDialog progressDialog = null;

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				MainHome_Activity.showLoading();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				MainHome_Activity.hideLoading();
				super.onPageFinished(view, url);
			}

		});

		// Javascript inabled on webview
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(link);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		switch (id) {
		case R.id.iv_watch_now_view:
			SetupApp.setText(R.id.tv_watch_now,
					getString(R.string.s_live_stream), getActivity());
			tv_watch.setVisibility(View.GONE);
			webView.setVisibility(View.VISIBLE);
			webView.loadUrl(link);
			break;
		default:
			break;
		}
	}

	@Override
	public void showResult(JSONObject result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showResult(String xml) {
		MainHome_Activity.hideLoading();
		if (xml != null && !xml.isEmpty()) {
			try {
				Log.i("Hoa debug", "xml =" + xml.toString());
				XMLParserHelper parser = new XMLParserHelper(xml, null);
				String stationEnabled = parser.getAttributeOfTag("Station",
						"StationEnabled");
				link = parser.getAttributeOfTag("Station", "StationURL");
				if (stationEnabled.equals("true")) {
					webView.setVisibility(View.VISIBLE);
					tv_msg_streamlive.setVisibility(View.GONE);
					initData();
					initControls();
					return;
				}

			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		webView.setVisibility(View.GONE);
		if (getActivity() != null)
			SetupApp.showMessageOk(getActivity(), "Intocable",
					"There are no streaming events today, keep checking back for more.");
		// tv_msg_streamlive.setVisibility(View.VISIBLE);

	}

	@Override
	public void onDestroy() {
		if (xmlGetter != null && !xmlGetter.isCancelled()) {
			xmlGetter.cancel(true);
			xmlGetter = null;
		}
		super.onDestroy();
	}

}
