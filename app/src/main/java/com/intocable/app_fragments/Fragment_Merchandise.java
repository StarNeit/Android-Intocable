package com.intocable.app_fragments;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.res.Configuration;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;


import com.androidquery.AQuery;
import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.adapter.Merchandise_Adapter;
import com.intocable.object.Merchandise;
import com.intocable.utils.AppFragment;
import com.intocable.utils.CommonMethods;
import com.intocable.utils.CommonVariables;
import com.intocable.utils.SetupApp;
import com.intocable.ws.GetXmlWeb;
import com.intocable.ws.OnResultAsyncTask;

public class Fragment_Merchandise extends AppFragment implements
		OnResultAsyncTask {

	private ImageView ivBanner;

	private Merchandise_Adapter adapter;
	private GridView gr_merchandise;

	public static String linkBanner = "";

	private GetXmlWeb xmlGetter = null;

	private String url;

	private Configuration config;
	private AQuery mAQuery;
	private int dimenPor, dimenLand;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_merchandise, null);
		SetupApp.setHeader(getString(R.string.s_merchandise), getActivity());
		config = getResources().getConfiguration();
		dimenPor = (int) getResources().getDimension(R.dimen.layout_20);
		dimenLand = (int) getResources().getDimension(R.dimen.layout_20);
		mAQuery = new AQuery(getActivity());
		initmain(view);
		initData();
		return view;
	}

	private void initmain(View view) {
		// TODO Auto-generated method stub
		ivBanner = (ImageView) view.findViewById(R.id.ivMerchandise);
		gr_merchandise = (GridView) view.findViewById(R.id.gr_merchandise);
		gr_merchandise
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int arg2, long arg3) {
						Fragment_MerchandiseView.ID = CommonVariables.merchandises
								.get(arg2).getID();
						MainHome_Activity.m_FragmentActivity.set_Fragment(
								new Fragment_MerchandiseView(),
								R.id.content_frame, true);
					}
				});
	}

	private void initData() {
		if (CommonVariables.merchandises == null)
			CommonVariables.merchandises = new ArrayList<Merchandise>();
		adapter = new Merchandise_Adapter(getActivity(),
				CommonVariables.merchandises);
		gr_merchandise.setAdapter(adapter);
		setHeightGridview();
		getData();
	}

	private void getData() {
		if (xmlGetter != null && !xmlGetter.isCancelled())
			xmlGetter.cancel(true);

		if (CommonVariables.merchandises.size() == 0) {
			xmlGetter = new GetXmlWeb(this, GetXmlWeb.FOREGROUND,
					GetXmlWeb.TYPE_MERCHANDISE);
			MainHome_Activity.hideLoading();
		} else {
			xmlGetter = new GetXmlWeb(this, GetXmlWeb.BACKGROUND,
					GetXmlWeb.TYPE_MERCHANDISE);
			MainHome_Activity.showLoading();
		}
		xmlGetter.execute();
	}

	@Override
	public void showResult(JSONObject result) {

	}

	@Override
	public void showResult(String xml) {
		MainHome_Activity.hideLoading();
		if (xml != null && !xml.isEmpty()) {

			url = xmlGetter.getUrl();

			// parser
			CommonMethods.getMerchanise(url, xml);

			if (!linkBanner.isEmpty())
				mAQuery.id(ivBanner).image(linkBanner, false, true, 200, 0,
						null, AQuery.FADE_IN);

			adapter.notifyDataSetChanged();
			setHeightGridview();
		}
	}

	private void setHeightGridview() {
		if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
			SetupApp.setGridViewHeightBasedOnChildren(gr_merchandise, 3,
					dimenPor);
		} else if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			SetupApp.setGridViewHeightBasedOnChildren(gr_merchandise, 3,
					dimenLand);
		}
	}
}