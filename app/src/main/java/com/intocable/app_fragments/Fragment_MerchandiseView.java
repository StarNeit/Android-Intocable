package com.intocable.app_fragments;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.Website_Activity;
import com.intocable.utils.AppFragment;
import com.intocable.utils.SetupApp;

public class Fragment_MerchandiseView extends AppFragment {
	private ImageView iv_views_merchandise;
	private TextView tv_merchandise_view, tv_mm_view;
	public static String ID;
	private String s_buy_merchandise;
	private Bitmap bitmap;
	private String url;
	private AsyncTask<String, Void, Void> _merchandise_View_AsyncTask = null;
	Element m_element = null;
	private AQuery aQuery = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_merchandise_view, null);
		SetupApp.setHeader(getString(R.string.s_merchandise), getActivity());
		
		initmain(view);
		initData();
		aQuery = new AQuery(getActivity());
		return view;
	}

	void dismiss() {
		MainHome_Activity.hideLoading();
	}

	void show() {
		MainHome_Activity.showLoading();
	}

	void updateView() {
		if (m_element != null) {
			url = m_element.getAttribute("LargeImageURL");
			tv_merchandise_view.setText(m_element.getAttribute("Name"));
			tv_mm_view.setText("" + getNode("ProductPrice", m_element));
			s_buy_merchandise = m_element.getAttribute("Buy");
			aQuery.id(R.id.iv_views_merchandise).image(url);
		}
	}

	private void initmain(View view) {
		// TODO Auto-generated method stub
		iv_views_merchandise = (ImageView) view
				.findViewById(R.id.iv_views_merchandise);
		tv_merchandise_view = (TextView) view
				.findViewById(R.id.tv_merchandise_view);
		tv_mm_view = (TextView) view.findViewById(R.id.tv_mm_view);
		updateView();
	}

	private void initData() {
		// TODO Auto-generated method stub
		if (m_element == null) {
			show();
			_merchandise_View_AsyncTask = new Merchandise_View_AsyncTask()
					.execute("http://www.grupointocable.com/_ws/intocable/spanish/xml/productdetail/"
							+ ID + ".xml");
		}

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		switch (id) {
		case R.id.ll_buy_merchandise:
			Website_Activity.url = s_buy_merchandise;
			SetupApp.gotoActivity(getActivity(), Website_Activity.class);
			break;
		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		if (_merchandise_View_AsyncTask != null)
			_merchandise_View_AsyncTask.cancel(true);
		_merchandise_View_AsyncTask = null;
		dismiss();
		super.onDestroy();
	}

	// ------------------------- parse xml-------------------
	private class Merchandise_View_AsyncTask extends
			AsyncTask<String, Void, Void> {
		// ProgressDialog pDialog;
		NodeList nodelist;
		private AsyncTask<String, String, Bitmap> _loadImage = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressbar
			// pDialog = new ProgressDialog(getActivity());
			// pDialog.setMessage("Loading...");
			// pDialog.setIndeterminate(false);
			// pDialog.show();
		}

		@Override
		protected Void doInBackground(String... Url) {
			try {
				URL url = new URL(Url[0]);
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				// Download the XML file
				Document doc = db.parse(new InputSource(url.openStream()));
				doc.getDocumentElement().normalize();
				nodelist = doc.getElementsByTagName("Product");

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled() {
			// pDialog.dismiss();
			if (_loadImage != null)
				_loadImage.cancel(true);
			_loadImage = null;
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Void args) {
			for (int temp = 0; temp < nodelist.getLength(); temp++) {
				Node nNode = nodelist.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					m_element = (Element) nodelist.item(temp);
					updateView();
					dismiss();
				}
			}
		}

	}

	// getNode function
	private static String getNode(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	
}
