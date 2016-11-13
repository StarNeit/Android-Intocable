package com.intocable.app_fragments;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.utils.AppFragment;
import com.intocable.utils.CommonMethods;
import com.intocable.utils.CommonVariables;
import com.intocable.utils.ConfigApp;
import com.intocable.utils.SetupApp;
import com.intocable.ws.GetXmlWeb;
import com.intocable.ws.OnResultAsyncTask;

public class Fragment_About extends AppFragment implements OnResultAsyncTask {
	private TextView tv_about_app;
	private GetXmlWeb xmlGetter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_about, null);
		SetupApp.setHeader(getString(R.string.header_about), getActivity());

		initmain(view);
		return view;
	}

	private void initmain(View view) {
		MainHome_Activity.showLoading();
		// TODO Auto-generated method stub
		tv_about_app = (TextView) view.findViewById(R.id.tv_about_app);

		// tv_about_app.setText(Html.fromHtml(getString(R.string.s_about_app)));

		if (xmlGetter != null && !xmlGetter.isCancelled())
			xmlGetter.cancel(true);

		xmlGetter = new GetXmlWeb(this, GetXmlWeb.FOREGROUND,
				GetXmlWeb.TYPE_ABOUT);
		xmlGetter.execute();
	}

	@Override
	public void showResult(JSONObject result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showResult(String xml) {
		// TODO Auto-generated method stub
		MainHome_Activity.hideLoading();
		if (xml != null && !xml.isEmpty()) {
			// parser
			CommonMethods.getAbout(xml);

			tv_about_app.setText(Html.fromHtml(CommonVariables.about));
		}
	}

}
