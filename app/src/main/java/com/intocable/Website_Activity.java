package com.intocable;

import com.androidquery.AQuery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class Website_Activity extends Activity {

	public static String url;
	private WebView webView;
	private ProgressBar process;
	private int load_data;
	private AQuery aQuery;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_website);
		aQuery = new AQuery(this);

		// Get webview
		initmain();
		startWebView(url);

	}

	private void initmain() {
		webView = (WebView) findViewById(R.id.webView_load);
		process = (ProgressBar) findViewById(R.id.pb_loading);
	}

	private void startWebView(String url) {

		// Create new webview Client to show progress dialog
		// When opening a url or click on link

		webView.setWebViewClient(new WebViewClient() {
			ProgressDialog progressDialog = null;

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);

				aQuery.id(R.id.pb_loading).visibility(View.VISIBLE);

			}

			@Override
			public void onPageFinished(WebView view, String url) {

				aQuery.id(R.id.pb_loading).visibility(View.GONE);
				super.onPageFinished(view, url);
			}

		});

		// Javascript inabled on webview
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url);

	}

	// Open previous opened link from history on webview when back button
	// pressed

	@Override
	// Detect when the back button is pressed
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			// Let the system handle the back button
			super.onBackPressed();
		}
	}

}