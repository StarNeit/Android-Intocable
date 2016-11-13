package com.intocable.app_fragments;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.intocable.GCMIntentService;
import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.utils.AppFragment;
import com.intocable.utils.ConfigApp;
import com.intocable.utils.SetupApp;

public class Fragment_Notification extends AppFragment {
	private ToggleButton tg_notification;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_notification, null);
		SetupApp.setHeader(getString(R.string.s_notification), getActivity());

		initmain(view);
		return view;
	}

	private void initmain(View view) {
		// TODO Auto-generated method stub
		tg_notification = (ToggleButton) view
				.findViewById(R.id.tg_notification);
		tg_notification.setChecked(ConfigApp.KEY_NOTIFICATION == 1?true:false);
		tg_notification
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if(isChecked)
							ConfigApp.KEY_NOTIFICATION = 1;
						else
							ConfigApp.KEY_NOTIFICATION = 0;
						new SendIdOnOverServer().execute();
						SetupApp.saveShared(MainHome_Activity.sharedPrefs);
					}
				});
	}
	
	class SendIdOnOverServer extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				HttpResponse response = null;
				HttpParams httpParameters = new BasicHttpParams();
				HttpClient client = new DefaultHttpClient(httpParameters);
				//http://www.grupointocable.com/_ws/api/register-android.php?key=5583ba04d14de2e2a04c2ff7dcd7f504&gcm=1234123412341234&notification=0
				String url = "http://www.grupointocable.com/_ws/api/notifications-android.php?key="
						+ GCMIntentService.SENDER_KEY
						+ "&gcm="
						+ GCMIntentService.Registration_Id
						+ "&notification="
						+ ConfigApp.KEY_NOTIFICATION
						;
				Log.i("Send URL:", url);
				HttpGet request = new HttpGet(url);

				response = client.execute(request);

				BufferedReader rd = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));

				String webServiceInfo = "";
				while ((webServiceInfo = rd.readLine()) != null) {
					Log.d("****Status Log***", "Webservice: " + webServiceInfo);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			this.cancel(true);
			super.onPostExecute(result);
		}
		

	}

}
