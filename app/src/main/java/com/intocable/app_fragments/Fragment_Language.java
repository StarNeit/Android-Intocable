package com.intocable.app_fragments;

import java.util.Locale;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.utils.AppFragment;
import com.intocable.utils.ConfigApp;
import com.intocable.utils.SetupApp;

public class Fragment_Language extends AppFragment {

	private CheckBox ck_english, ck_spanish;

	private SharedPreferences sharedPrefs;
	private Locale myLocale;
	String lang = "en";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_language, null);
		SetupApp.setHeader(getString(R.string.s_language), getActivity());
		sharedPrefs = MainHome_Activity.sharedPrefs;

		initmain(view);
		initData();
		return view;
	}

	private void initmain(View view) {
		// TODO Auto-generated method stub
		ck_english = (CheckBox) view.findViewById(R.id.ck_english);
		ck_spanish = (CheckBox) view.findViewById(R.id.ck_spanish);
	}

	private void initData() {
		// TODO Auto-generated method stub

		if (ConfigApp.KEY_LANGUAGE.toString().equals("english/")) {
			ck_english.setChecked(true);
			ck_spanish.setChecked(false);
		} else {
			ck_english.setChecked(false);
			ck_spanish.setChecked(true);
		}

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		switch (id) {
		case R.id.llenglish:

			ck_english.setChecked(true);
			ck_spanish.setChecked(false);

			ConfigApp.KEY_LANGUAGE = "english/";
			SetupApp.changeLang(getActivity(), "en");
			SetupApp.saveShared(sharedPrefs);

			break;
		case R.id.llSpanish:

			ck_english.setChecked(false);
			ck_spanish.setChecked(true);

			ConfigApp.KEY_LANGUAGE = "spanish/";
			SetupApp.changeLang(getActivity(), "es");
			SetupApp.saveShared(sharedPrefs);

			break;

		default:
			break;
		}

	}
}
