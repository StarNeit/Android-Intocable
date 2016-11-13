package com.intocable.app_fragments;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.SimpleImageActivity;
import com.intocable.adapter.Facebook_Adapter;
import com.intocable.adapter.News_Adapter;
import com.intocable.adapter.Twitter_Adapter;
import com.intocable.app_fragments.Fragment_Media.ViewHolder;
import com.intocable.controller.ImagePagerFragment;
import com.intocable.object.ChildSection;
import com.intocable.object.Facebook;
import com.intocable.object.News;
import com.intocable.object.Twitter;
import com.intocable.utils.AppFragment;
import com.intocable.utils.AsyncTaskHelper;
import com.intocable.utils.CommonVariables;
import com.intocable.utils.ConfigApp;
import com.intocable.utils.SetupApp;
import com.intocable.ws.WsIntoCable;

public class Fragment_News extends AppFragment {

	public static int tab_select_news = 0;

	private View view_news_tab, view_facebook_tab, view_tweets_tab;

	private ListView lv_news;
	private WebView webview_facebook;
	private String Link_fb = "https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fgrupointocable&width=580&height=800&show_faces=false&colorscheme=light&stream=true&border_color=0&header=false&appId=290904444377323";

	private ArrayList<Object> list_news = new ArrayList<Object>();
	private News_AsyncTask new_asyntask = null;
	NodeList nodelist;

	public static JSONObject jsionTwiter, jisonFacebook;

	public String[] Url;
	public String url;

	SimpleDateFormat formatGetNews = new SimpleDateFormat("MMM dd, yyyy");
	SimpleDateFormat formatGet = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	SimpleDateFormat formatGet2 = new SimpleDateFormat(
			"EEE MMM dd HH:mm:ss Z yyyy");
	SimpleDateFormat formatSet = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	// data load from server
	private ArrayList<Facebook> list_facebook = new ArrayList<Facebook>();
	private ArrayList<Twitter> list_twiter = new ArrayList<Twitter>();
	// private JSONObject dataFacebook = null;

	private JSONObject dataTwiter = null;

	private NewsViewAdapter adapterNews;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_news, null);
		SetupApp.setHeader(getActivity().getString(R.string.s_news),
				getActivity());
		initmain(view);
		initData();
		return view;
	}

	public void webview() {
		MainHome_Activity.hideLoading();
		webview_facebook.getSettings().setJavaScriptEnabled(true); // enable
																	// javascript

		webview_facebook.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT)
						.show();
			}
		});

		webview_facebook.loadUrl(Link_fb);

	}

	private void initmain(View view) {
		// TODO Auto-generated method stub
		view_news_tab = (View) view.findViewById(R.id.view_news_tab);
		view_facebook_tab = (View) view.findViewById(R.id.view_facebook_tab);
		view_tweets_tab = (View) view.findViewById(R.id.view_tweets_tab);
		webview_facebook = (WebView) view.findViewById(R.id.wv_facebook);
		lv_news = (ListView) view.findViewById(R.id.lv_news);
		lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (tab_select_news == 0) {

					Object obj = list_news.get(arg2);
					if (!(obj instanceof News))
						return;

					News news = (News) obj;
					Fragment_NewsView.url_image = news.getLargeImage();
					Fragment_NewsView.s_news = news.getBody();

				} else if (tab_select_news == 1) {
					// Fragment_NewsView.url_image = list_facebook.get(arg2)
					// .getImage();
					// Fragment_NewsView.s_news = list_facebook.get(arg2)
					// .getContent();
					lv_news.setVisibility(View.GONE);
					webview();

				} else if (tab_select_news == 2) {

					Fragment_NewsView.url_image = list_twiter.get(arg2)
							.getLargeImage();
					Fragment_NewsView.s_news = list_twiter.get(arg2).getBody();
				}

				MainHome_Activity.m_FragmentActivity.set_Fragment(
						new Fragment_NewsView(), R.id.content_frame, true);

			}
		});
		adapterNews = new NewsViewAdapter(getActivity(), tab_select_news);
		lv_news.setAdapter(adapterNews);
		updatelistview_news();
	}

	private void initData() {
		// TODO Auto-generated method stub

		if (SetupApp.checkInternetConnection(getActivity()))
			load_data();
		else
			return;
	}

	private void load_data() {
		// TODO Auto-generated method stub
		// MainHome_Activity.showLoading();
		if (tab_select_news == 0) {

			if (webview_facebook.getVisibility() == View.VISIBLE) {

				webview_facebook.setVisibility(View.GONE);
				lv_news.setVisibility(View.VISIBLE);
			}
			if (list_news == null || list_news.size() == 0) {
				view_news_tab.setBackgroundResource(R.color.blue);
				view_facebook_tab.setBackgroundResource(R.color.silver);
				view_tweets_tab.setBackgroundResource(R.color.silver);
				new_asyntask = new News_AsyncTask(getActivity(),
						News_AsyncTask.TYPE_GET_NEWS);
				if (SetupApp.checkInternetConnection(getActivity()))
					new_asyntask.execute();
			}

		} else if (tab_select_news == 1) {
			// && (list_facebook == null || list_facebook.size() == 0)) {
			if (lv_news.getVisibility() == View.VISIBLE) {
				lv_news.setVisibility(View.GONE);
				webview_facebook.setVisibility(View.VISIBLE);
			}
			webview();
			// view_news_tab.setBackgroundResource(R.color.silver);
			// view_facebook_tab.setBackgroundResource(R.color.blue);
			// view_tweets_tab.setBackgroundResource(R.color.silver);
			// new_asyntask = new News_AsyncTask(getActivity(),
			// News_AsyncTask.TYPE_GET_FACEBOOK);
			// if (SetupApp.checkInternetConnection(getActivity()))
			// new_asyntask.execute();

		} else {
			if (tab_select_news == 2) {
				if (webview_facebook.getVisibility() == View.VISIBLE) {

					webview_facebook.setVisibility(View.GONE);
					lv_news.setVisibility(View.VISIBLE);
				}
				if (list_twiter == null || list_twiter.size() == 0) {
					view_news_tab.setBackgroundResource(R.color.silver);
					view_facebook_tab.setBackgroundResource(R.color.silver);
					view_tweets_tab.setBackgroundResource(R.color.blue);
					new_asyntask = new News_AsyncTask(getActivity(),
							News_AsyncTask.TYPE_GET_TWITER);
					if (SetupApp.checkInternetConnection(getActivity()))
						new_asyntask.execute();

				}
			}

		}

		updatelistview_news();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		switch (id) {
		case R.id.ll_news_tab:

			view_news_tab.setBackgroundResource(R.color.blue);
			view_facebook_tab.setBackgroundResource(R.color.silver);
			view_tweets_tab.setBackgroundResource(R.color.silver);
			tab_select_news = 0;
			load_data();
			break;
		case R.id.ll_facebook_tab:
			view_news_tab.setBackgroundResource(R.color.silver);
			view_facebook_tab.setBackgroundResource(R.color.blue);
			view_tweets_tab.setBackgroundResource(R.color.silver);
			tab_select_news = 1;
			load_data();
			break;
		case R.id.ll_tweets_tab:
			view_news_tab.setBackgroundResource(R.color.silver);
			view_facebook_tab.setBackgroundResource(R.color.silver);
			view_tweets_tab.setBackgroundResource(R.color.blue);
			tab_select_news = 2;
			load_data();
			break;

		default:
			break;
		}
	}

	// ------------------------- parse xml-------------------
	private class News_AsyncTask extends AsyncTaskHelper {
		public static final int TYPE_GET_NEWS = 0;
		public static final int TYPE_GET_FACEBOOK = 1;
		public static final int TYPE_GET_TWITER = 2;

		int type_load = 0;
		private JSONObject dataFacebook = null;

		public News_AsyncTask(Activity activity, int type_load) {
			super(activity);
			this.type_load = type_load;
			// this.time_limit = 120 * 1000;
		}

		@Override
		protected Void doInBackground(Void... arg) {
			switch (type_load) {
			case TYPE_GET_NEWS:
				try {
					// U Url;
					URL url = new URL(ConfigApp.link_ws("news"));
					DocumentBuilderFactory dbf = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					// Download the XML file
					Document doc = db.parse(new InputSource(url.openStream()));
					doc.getDocumentElement().normalize();
					if (tab_select_news == 0) {
						nodelist = doc.getElementsByTagName("Article");
					} else if (tab_select_news == 1) {
					} else {

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case TYPE_GET_FACEBOOK:
				dataFacebook = WsIntoCable
						.getfacebook("https://graph.facebook.com/grupointocable/posts?access_token=271631279687826%7CfygJETfNjrXBwFQj2vi_4d3WuL4");

				break;
			case TYPE_GET_TWITER:
				dataTwiter = WsIntoCable
						.getTwiter("http://www.grupointocable.com/_ws/twitter_feed/send_twitter.php?twitter_str=920FBFOJHTGIEI2UE2E584");
				break;

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			MainHome_Activity.hideLoading();
			switch (type_load) {
			case TYPE_GET_NEWS:
				list_news.clear();
				if (nodelist != null) {
					for (int temp = 0; temp < nodelist.getLength(); temp++) {
						Node nNode = nodelist.item(temp);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element e = (Element) nodelist.item(temp);
							if (tab_select_news == 0) {
								News mNews = new News();
								mNews.setTitle("" + getNode("Title", e));
								mNews.setBody("<body>" + getNode("Body", e)
										+ "</body>");
								mNews.setPosted("" + e.getAttribute("Posted"));
								mNews.setSmallImage(""
										+ e.getAttribute("SmallImage"));
								mNews.setLargeImage(""
										+ e.getAttribute("LargeImage"));

								try {
									mNews.setDatePosted(formatGetNews
											.parse(mNews.getPosted().trim()));
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									mNews.setDatePosted(new Date());
								}
								list_news.add(mNews);

							}
						}
					}
				}
				updatelistview_news();
				break;
			case TYPE_GET_FACEBOOK:
				try {

					int length_fb = 0;
					Log.d("LAN DEBUG", dataFacebook.toString());

					JSONArray arrFacebook = dataFacebook.getJSONArray("data");
					Log.d("LAN DEBUG", arrFacebook.toString());
					length_fb = arrFacebook.length();
					list_facebook.clear();
					for (int i = 0; i < length_fb; i++) {

						JSONObject row = arrFacebook.getJSONObject(i);
						Facebook mfacebook = new Facebook();
						mfacebook.setTitle(row.getString("message"));
						String time = row.getString("updated_time");
						try {
							Date d = formatGet.parse(time);
							time = formatSet.format(d);
						} catch (ParseException e) {

						}
						mfacebook.setTime(time);
						mfacebook.setContent(row.getString("message"));
						list_facebook.add(mfacebook);
					}
					updatelistview_news();
				} catch (JSONException er) {
					// TODO Auto-generated catch block
					er.printStackTrace();
				}
				break;
			case TYPE_GET_TWITER:
				try {
					JSONArray arrFacebook = dataTwiter.getJSONArray("statuses");
					int length = 0;
					length = arrFacebook.length();
					list_twiter.clear();
					for (int i = 0; i < length; i++) {
						Twitter mtwitter = new Twitter();
						JSONObject row = arrFacebook.getJSONObject(i);
						mtwitter.setBody(row.getString("text"));

						String time = row.getString("created_at");
						try {
							Date d = formatGet2.parse(time);
							time = formatSet.format(d);
						} catch (ParseException e) {

						}

						mtwitter.setTitle(time);
						mtwitter.setLargeImage(row.getJSONObject("user")
								.getString("profile_background_image_url"));

						list_twiter.add(mtwitter);
					}
					updatelistview_news();
				} catch (JSONException er) {
					// TODO Auto-generated catch block
					er.printStackTrace();
				}

				break;
			default:
				break;

			}
			super.onPostExecute(result);
		}
	}

	private void updatelistview_news() {
		// TODO Auto-generated method stub
		adapterNews.setType(tab_select_news);
		adapterNews.notifyDataSetChanged();
	}

	// getNode function
	private static String getNode(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	public class NewsViewAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private AQuery mAQuery;
		private String url = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpa1/v/t1.0-1/c17.17.212.212/s200x200/66722_10151361136486709_544848419_n.jpg?oh=6440632bc31d7dd36db42f9e7c6deba4&oe=54DB88F2&__gda__=1423972273_ed85c55bfb977c6a8aa4ced27d764cac";

		private int type = 0;

		public NewsViewAdapter(Context context, int type) {
			this.type = type;
			mInflater = LayoutInflater.from(context);
			mAQuery = new AQuery(context);
			if (type == 0) {
				sortList();
			}
		}

		public void setType(int type) {
			this.type = type;
			if (type == 0) {
				sortList();
			}
		}

		public int getCount() {
			if ((type == 0) && (list_news != null))
				return list_news.size();
			else if ((type == 1) && (list_facebook != null))
				return list_facebook.size();
			else if ((type == 2) && (list_twiter != null))
				return list_twiter.size();
			return 0;
		}

		public Object getItem(int position) {
			if ((type == 0) && (list_news != null))
				return list_news.get(position);
			else if ((type == 1) && (list_facebook != null))
				return list_facebook.get(position);
			else if ((type == 2) && (list_twiter != null))
				return list_twiter.get(position);
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public void sortList() {
			// sort alphabetical
			Collections.sort(list_news, new Comparator<Object>() {
				@Override
				public int compare(Object left, Object right) {
					// TODO Auto-generated method stub
					if (left instanceof News && right instanceof News) {
						return ((News) left).getDatePosted().compareTo(
								((News) right).getDatePosted())
								* -1;
					} else
						return 0;
				}
			});

			// add first section
			String prefix = "";
			for (int i = 0; i < list_news.size(); i++) {
				if (list_news.get(i) instanceof News) {
					prefix = ((News) list_news.get(i)).getPosted();
					list_news.add(i, new ChildSection(prefix));
					break;
				} else if (list_news.get(i) instanceof ChildSection) {
					prefix = ((ChildSection) list_news.get(i)).getTitle();
					break;
				}
			}

			// add section
			for (int i = 1; i < list_news.size(); i++) {
				if (list_news.get(i) instanceof News) {
					News item = (News) list_news.get(i);
					if (item.getPosted().equals(prefix))
						continue;
					prefix = item.getPosted();
					list_news.add(i, new ChildSection(prefix));
				} else if (list_news.get(i) instanceof ChildSection) {
					prefix = ((ChildSection) list_news.get(i)).getTitle();
					continue;
				}
			}
		}

		public void setData(ArrayList<Object> list_news) {
			list_news = list_news;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = null;
			if (type == 0) {
				Object obj = list_news.get(position);
				if (obj instanceof ChildSection) {
					ChildSection section = (ChildSection) obj;
					convertView = mInflater.inflate(R.layout.row_sub_events,
							null);
					TextView tv_sub_events = (TextView) convertView
							.findViewById(R.id.tv_sub_events);
					tv_sub_events.setText(section.getTitle());
				} else if (obj instanceof News) {
					News item = (News) obj;
					if (list_news.get(position - 1) instanceof ChildSection)
						convertView = null;

					if (convertView == null || convertView.getTag() == null) {
						convertView = mInflater
								.inflate(R.layout.row_news, null);
						ImageView ivNews = (ImageView) convertView
								.findViewById(R.id.iv_row_news);
						TextView tvTitle = (TextView) convertView
								.findViewById(R.id.tv_title_news);
						TextView tvBody = (TextView) convertView
								.findViewById(R.id.tv_body_news);
						TextView tvTime = (TextView) convertView
								.findViewById(R.id.tv_time_news);
						ivNews = (ImageView) convertView
								.findViewById(R.id.iv_row_news);
						tvTitle = (TextView) convertView
								.findViewById(R.id.tv_title_news);
						tvBody = (TextView) convertView
								.findViewById(R.id.tv_body_news);
						tvTime = (TextView) convertView
								.findViewById(R.id.tv_time_news);

						tvTitle.setText(item.getTitle());
						tvBody.setText(Html.fromHtml(item.getBody()));
						tvTime.setText(item.getPosted());

						String url = item.getSmallImage();
						mAQuery.id(ivNews).image(url, true, true, 200, 0, null,
								AQuery.FADE_IN);
					}

				}

			} else if (type == 1) {
				Facebook item = list_facebook.get(position);

				convertView = mInflater.inflate(R.layout.row_facebook, null);

				ImageView ivFacebook = (ImageView) convertView
						.findViewById(R.id.iv_row_news);
				TextView tvTitle = (TextView) convertView
						.findViewById(R.id.tv_title_news);
				TextView tvTime = (TextView) convertView
						.findViewById(R.id.tv_time_news);

				mAQuery.id(tvTitle).text(
						Html.fromHtml((String) item.getTitle()).toString());
				mAQuery.id(tvTime).text(item.getTime());
				mAQuery.id(ivFacebook).image(url, true, true, 200, 0, null,
						AQuery.FADE_IN);

			} else if (type == 2) {
				Twitter item = list_twiter.get(position);
				convertView = mInflater.inflate(R.layout.row_facebook, null);
				ImageView ivTweets = (ImageView) convertView
						.findViewById(R.id.iv_row_news);
				TextView tvTitle = (TextView) convertView
						.findViewById(R.id.tv_title_news);
				TextView tvTime = (TextView) convertView
						.findViewById(R.id.tv_time_news);
				tvTitle.setText(Html.fromHtml(item.getBody()));

				tvTime.setText(item.getTitle());

				mAQuery.id(ivTweets).image(item.getLargeImage(), true, true,
						200, 0, null, AQuery.FADE_IN);

			}
			return convertView;
		}
	}

	class ViewHolder {
		ImageView imageview;
	}
}