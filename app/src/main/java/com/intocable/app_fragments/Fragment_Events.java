package com.intocable.app_fragments;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.adapter.Event_Adapter_1;
import com.intocable.object.Events;
import com.intocable.utils.AppFragment;
import com.intocable.utils.ConfigApp;
import com.intocable.utils.SetupApp;

public class Fragment_Events extends AppFragment {

	private ListView lv_events;
	NodeList nodelist;
	public static ArrayList<Object> listChild = new ArrayList<Object>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_events, null);
		SetupApp.setHeader(getString(R.string.s_events), getActivity());

		initmain(view);
		initData();
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		String url = ConfigApp.link_ws("events");
		if (SetupApp.checkInternetConnection(getActivity()))
			new Events_AsyncTask().execute(url);
	}

	private void initmain(View view) {
		// TODO Auto-generated method stub
		lv_events = (ListView) view.findViewById(R.id.lv_events);
		lv_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				if (pos % 2 != 0) {
					Object obj = listChild.get(pos);
					Events child = (Events) obj;
					Fragment_Events_Details.title = child.getTitle();
					Fragment_Events_Details.s_day = child.getDay();
					Fragment_Events_Details.s_time = child.getTime();
					Fragment_Events_Details.ID = child.getID();
					Fragment_Events_Details.tab_select = 0;
					Fragment_Events_Details.s_city = child.getState() + ", "
							+ child.getCity();
					MainHome_Activity.m_FragmentActivity.set_Fragment(
							new Fragment_Events_Details(), R.id.content_frame,
							true);
				}
			}
		});
	}

	// ------------------------- parse xml-------------------
	private class Events_AsyncTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			MainHome_Activity.showLoading();
		}

		@SuppressWarnings("unused")
		protected void onProgressUpdate(Integer... progress) {
			MainHome_Activity.showLoading();
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
				nodelist = doc.getElementsByTagName("Events").item(0)
						.getChildNodes();

				// nodelist = doc.getElementsByTagName("Date");

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(Void args) {

			listChild.clear();
			for (int temp = 0; temp < nodelist.getLength(); temp++) {
				Node date = nodelist.item(temp);

				// day
				String day = ((Element) date).getAttribute("Day");

				// list event
				NodeList listEvent = date.getChildNodes();
				for (int j = 0; j < listEvent.getLength(); j++) {

					Node event = listEvent.item(j);
					Log.i("Hoa debug", "-->" + event.getTextContent());
					String eventId = ((Element) event).getAttribute("ID");
					String eventTime = ((Element) event).getAttribute("Time");

					String title = getNode("Title", (Element) event);

					Node adress = event.getLastChild();
					String city = ((Element) adress).getAttribute("City");
					String state = ((Element) adress).getAttribute("State");
					String zip = ((Element) adress).getAttribute("Zip");

					Events mEvents = new Events();
					mEvents.setID(eventId);
					mEvents.setTitle(title);
					mEvents.setTime(eventTime);
					mEvents.setDay(day);
					mEvents.setCity(city);
					mEvents.setState(state);
					listChild.add(mEvents);

				}
				updatelistview();
			}
			MainHome_Activity.hideLoading();
		}

	}

	private void updatelistview() {
		// TODO Auto-generated method stub
		Event_Adapter_1 adapter = null;
		if (getActivity() != null)
			adapter = new Event_Adapter_1(getActivity(),
					android.R.layout.simple_list_item_1, listChild);
		lv_events.setAdapter(adapter);
	}

	// getNode function
	private static String getNode(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

}
