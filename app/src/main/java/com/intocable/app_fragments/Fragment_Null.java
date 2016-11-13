package com.intocable.app_fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intocable.utils.AppFragment;
import com.intocable.utils.SetupApp;

public class Fragment_Null extends AppFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		FragmentManager fm = getFragmentManager();
		fm.popBackStack();
		return null;
	}
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

}
