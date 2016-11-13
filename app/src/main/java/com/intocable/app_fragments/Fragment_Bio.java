package com.intocable.app_fragments;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.utils.AppFragment;
import com.intocable.utils.CommonMethods;
import com.intocable.utils.CommonVariables;
import com.intocable.utils.SetupApp;
import com.intocable.ws.GetXmlWeb;
import com.intocable.ws.OnResultAsyncTask;
import com.intocable.ws.WsIntoCable;

public class Fragment_Bio extends AppFragment implements OnResultAsyncTask {
	private ImageView iv_bio;
	private WebView viewBio;

	public static GetXmlWeb xmlGetter = null;
	private String url;

	private AQuery mAQuery;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_bio, null);
		SetupApp.setHeader(getString(R.string.s_bio), getActivity());

		mAQuery = new AQuery(getActivity());

		initmain(view);
		initData();
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// has data
		if (CommonVariables.bio != null) {
			// check language

			// - same
			if (CommonVariables.bio.getUrl().equals(url)) {
				// set data
				viewBio.loadDataWithBaseURL(null, CommonVariables.bio.getBio(),
						"text/html", HTTP.UTF_8, null);
				mAQuery.id(iv_bio).image(CommonVariables.bio.getUrlImage(),
						false, true, 200, 0, null, AQuery.FADE_IN);

				// get data in background
				getData(GetXmlWeb.BACKGROUND);
			}
			// - not same => get new data
			else {
				getData(GetXmlWeb.FOREGROUND);
			}
		}
		// no data => get data
		else {
			getData(GetXmlWeb.FOREGROUND);
		}
	}

	private void initmain(View view) {
		// TODO Auto-generated method stub
		iv_bio = (ImageView) view.findViewById(R.id.iv_bio);
		viewBio = (WebView) view.findViewById(R.id.viewBio);

	}

	private void initData() {
		// TODO Auto-generated method stub

		url = WsIntoCable.getLink(WsIntoCable.TYPE_BIO);
		// new Bio_AsyncTask().execute(url);
	}

	private void getData(int type_load) {
		// goi cai webservice
		MainHome_Activity.showLoading();
		if (xmlGetter != null && !xmlGetter.isCancelled())
			xmlGetter.cancel(true);
		xmlGetter = new GetXmlWeb(this, type_load, GetXmlWeb.TYPE_BIO);
		xmlGetter.execute();
	}

	@Override
	public void showResult(JSONObject result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showResult(String xml) {
		// vao day
		MainHome_Activity.hideLoading();
		// TODO Auto-generated method stub

		// result from asynctask
		if (xml != null && !xml.isEmpty()) {

			// parser cho nay
			boolean changeData = CommonMethods.getBio(url, xml);

			if (changeData) {
				if (CommonVariables.bio != null) {
					MainHome_Activity.showLoading();

					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							if (getActivity() != null)
								getActivity().runOnUiThread(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										// set data
										viewBio.loadDataWithBaseURL(null,
												CommonVariables.bio.getBio(),
												"text/html", HTTP.UTF_8, null);
										mAQuery.id(iv_bio).image(
												CommonVariables.bio
														.getUrlImage(), false,
												true, 200, 0, null,
												AQuery.FADE_IN);
									}
								});

							// MainHome_Activity.hideLoading();
						}
					}).start();
					MainHome_Activity.hideLoading();
				}
			}
		}
	}

}
