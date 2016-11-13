package com.intocable.utils;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class AppFragment extends Fragment {
	protected View m_View = null;
	protected AppFragment currentFragment = null;

	public void onClick(View v) {

	}

	public void add_subFragment(Fragment fragment, int id_page_fragment) {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(id_page_fragment, fragment);
		ft.commit();
	}

	public void set_subFragment(Fragment fragment, int id_page_fragment) {
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(id_page_fragment, fragment).commit();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void finish() {
		// TODO Auto-generated method stub
		
	}

	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
	public void onConfigurationChanged(Configuration newConfig) {
		
	}
}
