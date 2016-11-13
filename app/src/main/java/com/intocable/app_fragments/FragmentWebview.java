package com.intocable.app_fragments;

import com.intocable.R;
import com.intocable.utils.SetupApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class FragmentWebview extends Fragment {
	private WebView webView;

	public View onCreate(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news, null);
		webView = (WebView) view.findViewById(R.id.wv_facebook);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://www.google.com");
		return view;

	}

}
