package com.intocable.app_fragments;

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
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.utils.AppFragment;
import com.intocable.utils.ConfigApp;
import com.intocable.utils.SetupApp;

public class Fragment_AutomaticUpdate extends AppFragment {
	private ToggleButton tg_auto_update;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_update, null);
		SetupApp.setHeader(getString(R.string.header_automatically_update),
				getActivity());

		initmain(view);

		return view;
	}

	private void initmain(View view) {
		// TODO Auto-generated method stub
		tg_auto_update = (ToggleButton) view.findViewById(R.id.tg_auto_update);

		tg_auto_update
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
					}
				});

	}

}
