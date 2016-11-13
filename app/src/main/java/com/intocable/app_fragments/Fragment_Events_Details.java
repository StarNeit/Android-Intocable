package com.intocable.app_fragments;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.intocable.MainHome_Activity;
import com.intocable.Post_Comment_Activity;
import com.intocable.R;
import com.intocable.Website_Activity;
import com.intocable.adapter.Comment_Adapter;
import com.intocable.adapter.Music_Adapter_1;
import com.intocable.object.Comments;
import com.intocable.object.Music;
import com.intocable.utils.AppFragment;
import com.intocable.utils.CommonMethods;
import com.intocable.utils.CommonVariables;
import com.intocable.utils.SetupApp;
import com.intocable.utils.XMLParserHelper;
import com.intocable.ws.GetXmlWeb;
import com.intocable.ws.OnResultAsyncTask;

public class Fragment_Events_Details extends AppFragment implements
		OnResultAsyncTask {

	private ImageView iv_flyer;
	private LinearLayout ll_info, ll_post_cmt;
	private TextView tv_title_details, tv_date_time_details;
	private LinearLayout viewFlyer;
	public static String title, s_day, s_time, ID, s_city;
	private String s_ViewMap, s_Purchase_Tickets, s_Check_In;
	NodeList nodelist;
	private String link_image = null;
	public static int tab_select = 0;
	private Bitmap bitmap;
	private AQuery aQuery = null;
	private ListView lv_comments;
	private Comment_Adapter adapter;
	public static GetXmlWeb xmlGetter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_details_events, null);
		SetupApp.setHeader(title, getActivity());
		aQuery = new AQuery(getActivity());
		initmain(view);
		initData();
		return view;
	}

	@Override
	public void onResume() {
		select_tab();
		super.onResume();
	}

	private void initmain(View view) {
		iv_flyer = (ImageView) view.findViewById(R.id.iv_flyer);
		ll_info = (LinearLayout) view.findViewById(R.id.ll_info);
		ll_post_cmt = (LinearLayout) view.findViewById(R.id.ll_post_cmt);
		tv_title_details = (TextView) view.findViewById(R.id.tv_title_details);
		tv_date_time_details = (TextView) view
				.findViewById(R.id.tv_date_time_details);
		viewFlyer = (LinearLayout) view.findViewById(R.id.ll_viewFlyer);
		lv_comments = (ListView) view.findViewById(R.id.list_cmt);
		
	}

	private void select_tab() {
		Log.i("Hoa debug", "select_tab() = " + tab_select);
		if (tab_select == 0) {

			aQuery.id(R.id.ll_flyer_tab).background(
					R.drawable.bg_tab_left_hight);
			aQuery.id(R.id.ll_info_tab).background(
					R.drawable.bg_tab_center_normal);
			aQuery.id(R.id.ll_comments_tab).background(
					R.drawable.bg_tab_right_normal);
			viewFlyer.setVisibility(View.VISIBLE);
			ll_info.setVisibility(View.GONE);
			ll_post_cmt.setVisibility(View.GONE);
			if (bitmap == null) {
				loadData();
			} else {
				iv_flyer.setImageBitmap(bitmap);
			}
			Log.i("Hoa debug", "select_tab() 1= " + tab_select);
		} else if (tab_select == 1) {
			aQuery.id(R.id.ll_flyer_tab).background(
					R.drawable.bg_tab_left_normal);
			aQuery.id(R.id.ll_info_tab).background(
					R.drawable.bg_tab_center_hight);
			aQuery.id(R.id.ll_comments_tab).background(
					R.drawable.bg_tab_right_normal);
			viewFlyer.setVisibility(View.GONE);
			ll_info.setVisibility(View.VISIBLE);
			ll_post_cmt.setVisibility(View.GONE);
			loadData();
		} else if (tab_select == 2) {
			aQuery.id(R.id.ll_flyer_tab).background(
					R.drawable.bg_tab_left_normal);
			aQuery.id(R.id.ll_info_tab).background(
					R.drawable.bg_tab_center_normal);
			aQuery.id(R.id.ll_comments_tab).background(
					R.drawable.bg_tab_right_hight);
			viewFlyer.setVisibility(View.GONE);
			ll_info.setVisibility(View.GONE);
			ll_post_cmt.setVisibility(View.VISIBLE);
			loadData();
		}

	}

	private void initData() {
		tv_title_details.setText(title + "\n" + s_city);
		tv_date_time_details.setText(s_day + "\n" + s_time);
	}

	private void loadData() {
		Log.i("Log cmt", "" + ID);
		MainHome_Activity.showLoading();
		 new Events_Detail_AsyncTask()
		 .execute("http://www.grupointocable.com/_ws/intocable/english/xml/eventdetails/"
		 + ID + ".xml");

	}
	
	private void loadData_comment(){
		if (xmlGetter != null && !xmlGetter.isCancelled())
			xmlGetter.cancel(true);
		xmlGetter = new GetXmlWeb(this, GetXmlWeb.FOREGROUND,
				GetXmlWeb.TYPE_COMMENT);
		xmlGetter.eventId = ID;
		xmlGetter.execute();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		switch (id) {
		case R.id.ll_flyer_tab:
			tab_select = 0;
			select_tab();
			break;
		case R.id.ll_info_tab:
			tab_select = 1;
			select_tab();
			break;
		case R.id.ll_comments_tab:
			tab_select = 2;
			select_tab();
			break;
		case R.id.btn_post_cmt:
			Bundle bundle = new Bundle();
			bundle.putString("EVENT_ID", ID);
			SetupApp.gotoActivity(getActivity(), Post_Comment_Activity.class,
					bundle);

			break;
		case R.id.ll_view_map:
			Website_Activity.url = s_ViewMap;
			SetupApp.gotoActivity(getActivity(), Website_Activity.class);
			break;
		case R.id.ll_purchase_tickets:
			Website_Activity.url = s_Purchase_Tickets;
			SetupApp.gotoActivity(getActivity(), Website_Activity.class);
			break;
		case R.id.ll_check_in:
			Website_Activity.url = s_Check_In;
			SetupApp.gotoActivity(getActivity(), Website_Activity.class);
			break;
		case R.id.iv_flyer:
			showImageDialog();
			break;
		default:
			break;
		}
	}

	private void showImageDialog() {
		AlertDialog.Builder builder = new Builder(getActivity());

		ImageView ivView = new ImageView(getActivity());
		ivView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		ivView.setBackground(iv_flyer.getDrawable());
		builder.setView(ivView);

		builder.create().show();
	}

	// ------------------------- parse xml-------------------
	private class Events_Detail_AsyncTask extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressbar
			if (tab_select == 0) {

			} else {
				MainHome_Activity.showLoading();
			}
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
				if (tab_select == 0) {
					nodelist = doc.getElementsByTagName("Photo");
				} else if (tab_select == 1) {
					nodelist = doc.getElementsByTagName("Link");
				} else if (tab_select == 2) {
					nodelist = doc.getElementsByTagName("Comments");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(Void args) {
			MainHome_Activity.hideLoading();
			if (nodelist != null) {
				for (int temp = 0; temp < nodelist.getLength(); temp++) {
					Node nNode = nodelist.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) nodelist.item(temp);
						if (tab_select == 0) {
							new LoadImage().execute(e
									.getAttribute("LargeImageURL"));
						} else if (tab_select == 1) {
							if (e.getAttribute("Title").equals("View Map")) {
								s_ViewMap = e.getAttribute("ActionURL");
							} else if (e.getAttribute("Title").equals(
									"Purchase Tickets")) {
								s_Purchase_Tickets = e
										.getAttribute("ActionURL");
							} else if (e.getAttribute("Title").equals(
									"Check In")) {
								s_Check_In = e.getAttribute("ActionURL");
							}
						} else if (tab_select == 2) {
							
					     loadData_comment();

						}

					}

				}
			}
			if (tab_select == 0) {

			} else {
				MainHome_Activity.hideLoading();
			}
		}
	}

	@Override
	public void showResult(JSONObject result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showResult(String xml) {
		// TODO Auto-generated method stub
		// vao day
		MainHome_Activity.hideLoading();
		// TODO Auto-generated method stub

		// result from asynctask
		if (xml != null && !xml.isEmpty()) {

			// parser cho nay
			CommonMethods.getComment(xml);
			
			if (CommonVariables.comments == null)
				CommonVariables.comments = new ArrayList<Comments>();

			adapter = new Comment_Adapter(getActivity(), CommonVariables.comments);
			lv_comments.setAdapter(adapter);

			// co textview nao hien ra coi dat log di em
			
		}
	}

	// getNode function
	private static String getNode(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	// ----------------------load image--------------------------------
	private class LoadImage extends AsyncTask<String, String, Bitmap> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			MainHome_Activity.showLoading();
		}

		@SuppressWarnings("unused")
		protected void onProgressUpdate(Integer... progress) {
			MainHome_Activity.showLoading();
		}

		protected Bitmap doInBackground(String... args) {
			try {
				bitmap = BitmapFactory.decodeStream((InputStream) new URL(
						args[0]).getContent());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		protected void onPostExecute(Bitmap image) {
			if (image != null) {
				MainHome_Activity.hideLoading();
				iv_flyer.setImageBitmap(image);
			} else {
			}
		}
	}
}