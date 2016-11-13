package com.intocable.app_fragments;

import org.apache.http.protocol.HTTP;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.intocable.R;
import com.intocable.utils.AppFragment;
import com.intocable.utils.SetupApp;

public class Fragment_NewsView extends AppFragment {
	private SharedPreferences sharedPrefs = null;

	private ImageView iv_news_view;
	private WebView webview;
	private TextView tv_view_new;

	private AQuery mAQuery;
	public static String url_image;
	public static String s_news;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_view_news, null);
		mAQuery = new AQuery(getActivity());
		initmain(view);
		initData();
		return view;
	}

	private void initmain(View view) {
		// TODO Auto-generated method stub
		iv_news_view = (ImageView) view.findViewById(R.id.iv_news_view);
		tv_view_new = (TextView) view.findViewById(R.id.tv_view_new);
		webview = (WebView) view.findViewById(R.id.webView);
	}

	private void initData() {
		// TODO Auto-generated method stub
		if (url_image != null && !url_image.isEmpty()) {
			mAQuery.id(iv_news_view).image(url_image, false,
	                true, 200, 0, null, AQuery.FADE_IN);
		} else {
			iv_news_view.setVisibility(View.GONE);
		}

		webview.loadDataWithBaseURL(null, s_news, "text/html", HTTP.UTF_8, null);
		// tv_view_new.setText(Html.fromHtml(s_news));
	}

}
