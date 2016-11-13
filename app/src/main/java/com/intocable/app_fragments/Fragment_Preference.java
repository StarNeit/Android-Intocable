package com.intocable.app_fragments;

import java.util.Locale;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.utils.AppFragment;
import com.intocable.utils.ConfigApp;
import com.intocable.utils.SetupApp;

public class Fragment_Preference extends AppFragment {
	private SharedPreferences sharedPrefs;
	private Locale myLocale;
	String lang = "en";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_preference, null);
		SetupApp.setHeader(getString(R.string.s_preferences), getActivity());
		return view;
	}


	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		switch (id) {
		case R.id.ll_language:
			MainHome_Activity.m_FragmentActivity.set_Fragment(new Fragment_Language(), R.id.content_frame, true);
			break;
		case R.id.ll_notifications:
			MainHome_Activity.m_FragmentActivity.set_Fragment(new Fragment_Notification(), R.id.content_frame, true);
			break;
		case R.id.ll_update:
			MainHome_Activity.m_FragmentActivity.set_Fragment(new Fragment_AutomaticUpdate(), R.id.content_frame, true);
			break;
		case R.id.ll_about_application:
			MainHome_Activity.m_FragmentActivity.set_Fragment(new Fragment_About(), R.id.content_frame, true);
			break;

		default:
			break;
		}
	}
}
