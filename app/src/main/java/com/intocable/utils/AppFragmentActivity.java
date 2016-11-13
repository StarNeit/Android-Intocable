package com.intocable.utils;

import java.util.List;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.intocable.R;
import com.intocable.app_fragments.Fragment_Home;
import com.intocable.app_fragments.Fragment_Null;

public class AppFragmentActivity extends FragmentActivity {
	public static AppFragmentActivity m_FragmentActivity = null;
	protected static Boolean isLoad = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m_FragmentActivity = this;
	}

	public void onClick(View v) {
		FragmentManager fm = this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = null;
		try {
			List<Fragment> fragments = fm.getFragments();
			for (Fragment my_fragment : fragments) {
				if (my_fragment instanceof AppFragment
						&& !my_fragment.isHidden()) {
					((AppFragment) my_fragment).onClick(v);
				}
			}
		} catch (Exception exception) {

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		FragmentManager fm = this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = null;
		try {
			List<Fragment> fragments = fm.getFragments();
			for (Fragment my_fragment : fragments) {
				if (my_fragment instanceof AppFragment
						&& !my_fragment.isHidden()) {
					((AppFragment) my_fragment).onActivityResult(requestCode,
							resultCode, data);
				}
			}
		} catch (Exception exception) {

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		FragmentManager fm = this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = null;
		try {
			List<Fragment> fragments = fm.getFragments();
			for (Fragment my_fragment : fragments) {
				if (my_fragment instanceof AppFragment
						&& !my_fragment.isHidden()) {
					((AppFragment) my_fragment).onPause();
				}
			}
		} catch (Exception exception) {

		}

		super.onPause();
	}

	@Override
	public void onBackPressed() {
		FragmentManager fm = this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = null;
		try {
			List<Fragment> fragments = fm.getFragments();
			for (Fragment my_fragment : fragments) {
				if (my_fragment instanceof AppFragment
						&& !my_fragment.isHidden()) {
					((AppFragment) my_fragment).onBackPressed();
				}
			}
		} catch (Exception exception) {

		}

		super.onBackPressed();
	}

	@Override
	public void finish() {
		FragmentManager fm = this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = null;
		try {
			List<Fragment> fragments = fm.getFragments();
			for (Fragment my_fragment : fragments) {
				if (my_fragment instanceof AppFragment
						&& !my_fragment.isHidden()) {
					((AppFragment) my_fragment).finish();
				}
			}
		} catch (Exception exception) {

		}
		super.finish();
	}

	public Fragment getCurentFragment() {
		FragmentManager fm = this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = null;
		try {
			List<Fragment> fragments = fm.getFragments();
			for (Fragment my_fragment : fragments) {
				if (my_fragment instanceof AppFragment
						&& my_fragment.isVisible()) {
					return my_fragment;
				}
			}
		} catch (Exception exception) {

		}
		return null;
	}

	public void add_Fragment(Fragment fragment, int id_page_fragment) {
		String backStateName = fragment.getClass().getName();

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(id_page_fragment, fragment);
		// ft.addToBackStack(backStateName);
		ft.commit();
	}

	public void set_Fragment(Fragment fragment, int id_page_fragment) {
		set_Fragment(fragment, id_page_fragment, false);
	}

	public void set_Fragment(Fragment fragment, int id_page_fragment,
			Boolean isBackStack) {
		String backStateName = fragment.getClass().getName();
		FragmentManager manager = getSupportFragmentManager();
		boolean fragmentPopped = manager
				.popBackStackImmediate(backStateName, 0);
		if (!fragmentPopped) {
			FragmentTransaction ft = manager.beginTransaction();
			ft.replace(id_page_fragment, fragment);
			if (isBackStack == true) {
				ft.addToBackStack(backStateName);
			}

			ft.commit();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		FragmentManager fm = this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = null;
		try {
			List<Fragment> fragments = fm.getFragments();
			for (Fragment my_fragment : fragments) {
				if (my_fragment instanceof AppFragment
						&& my_fragment.isVisible()) {
					my_fragment.onConfigurationChanged(newConfig);
				}
			}
		} catch (Exception exception) {

		}
	}

}
