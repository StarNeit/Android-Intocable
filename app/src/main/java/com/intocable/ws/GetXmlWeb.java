package com.intocable.ws;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;

import com.intocable.R;
import com.intocable.utils.AppFragment;
import com.intocable.utils.SetupApp;

public class GetXmlWeb extends AsyncTask<Void, Void, Void> {

	public static final int FOREGROUND = 100;
	public static final int BACKGROUND = 101;

	public static final int TYPE_BIO = 1;
	public static final int POST_COMMENT = 2;
	public static final int TYPE_MUSIC = 3;
	public static final int TYPE_MERCHANDISE = 4;
	public static final int TYPE_PHOTO = 5;
	public static final int TYPE_VIDEO = 6;
	public static final int TYPE_ABOUT = 7;
	public static final int TYPE_LIVESTREAM = 8;
	public static final int TYPE_COMMENT = 9;

	private Activity activity;
	private int type;
	private int type_load;

	private Handler handler;
	private int timeLimit = 60 * 1000;

	private String url;

	private String resultXml = "";

	public String name = "", comment = "", eventId = "";

	private AppFragment _appFragment = null;

	public GetXmlWeb(Activity activity, int type_load, int type) {
		this.activity = activity;
		this.type = type;
		this.type_load = type_load;
	}

	public GetXmlWeb(AppFragment _appFragment, int type_load, int type) {
		this.activity = _appFragment.getActivity();
		this._appFragment = _appFragment;
		this.type = type;
		this.type_load = type_load;
	}

	public int getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		handler = new Handler();
		handler.postDelayed(r, timeLimit);
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		switch (type) {
		case TYPE_BIO:
			// chay cai nay, lay duoc xml
			url = WsIntoCable.getLink(WsIntoCable.TYPE_BIO);
			resultXml = WsIntoCable.getXML(url);
			break;
		case TYPE_MUSIC:
			url = WsIntoCable.getLink(WsIntoCable.TYPE_MUSIC);
			resultXml = WsIntoCable.getXML(url);
			break;
		case TYPE_MERCHANDISE:
			url = WsIntoCable.getLink(WsIntoCable.TYPE_MERCHANDISE);
			resultXml = WsIntoCable.getXML(url);
			break;
		case TYPE_PHOTO:
			url = WsIntoCable.getLink(WsIntoCable.TYPE_PHOTO);
			resultXml = WsIntoCable.getXML(url);
			break;
		case TYPE_VIDEO:
			url = WsIntoCable.getLink(WsIntoCable.TYPE_VIDEO);
			resultXml = WsIntoCable.getXML(url);
			break;
		case TYPE_LIVESTREAM:
			resultXml = WsIntoCable
					.getXML("http://grupointocable.com/_ws/intocable/any/xml/livestream/");
			break;
		case TYPE_ABOUT:
			url = WsIntoCable.getLink(WsIntoCable.TYPE_ABOUT);
			resultXml = WsIntoCable.getXML(url);
			break;
		case TYPE_COMMENT:
			url = WsIntoCable.getLink(WsIntoCable.TYPE_COMMENT).replace(
					"index", eventId);
			resultXml = WsIntoCable.getXML(url);
			break;
		case POST_COMMENT:
			if (!name.isEmpty() && !comment.isEmpty() && !eventId.isEmpty())
				resultXml = WsIntoCable.postComment(name, comment, eventId);
			else
				resultXml = "";
			break;
		default:
			resultXml = "";
			break;
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		handler.removeCallbacks(r);

		if (activity instanceof OnResultAsyncTask) {
			((OnResultAsyncTask) activity).showResult(resultXml);
		} else if (_appFragment != null
				&& _appFragment instanceof OnResultAsyncTask) {
			// goi show result
			((OnResultAsyncTask) _appFragment).showResult(resultXml);
		}
	}

	Runnable r = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			handler.removeCallbacks(this);

			if (activity instanceof OnResultAsyncTask) {
				((OnResultAsyncTask) activity).showResult(resultXml);
			} else if (_appFragment != null
					&& _appFragment instanceof OnResultAsyncTask) {
				((OnResultAsyncTask) _appFragment).showResult(resultXml);
			}

			if (type_load == FOREGROUND)
				if (SetupApp.checkInternetConnection(activity)) {
					SetupApp.showMessage(activity,
							activity.getString(R.string.error),
							activity.getString(R.string.error_request_time_out));

				} else {
					SetupApp.showMessage(activity,
							activity.getString(R.string.error),
							activity.getString(R.string.error_connect_internet));
				}
		}

	};
}
