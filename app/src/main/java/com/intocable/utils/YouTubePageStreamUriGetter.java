package com.intocable.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class YouTubePageStreamUriGetter extends
		AsyncTask<String, String, String> {
	ProgressDialog progressDialog;
	Activity activity;
	final String TAG = "AsyncTask Youtube";

	public YouTubePageStreamUriGetter(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected String doInBackground(String... params) {

		String url = params[0];

		try {
			String retVidUrl = null;
			ArrayList<Video> videos = Utils
					.getStreamingUrisFromYouTubePage(url);
			if (videos != null && !videos.isEmpty()) {
				for (Video video : videos) {
					if (video.ext.toLowerCase().contains("mp4")
							&& video.type.toLowerCase().contains("medium")) {
						retVidUrl = video.url;
						break;
					}
				}
				if (retVidUrl == null) {
					for (Video video : videos) {
						if (video.ext.toLowerCase().contains("3gp")
								&& video.type.toLowerCase().contains("medium")) {
							retVidUrl = video.url;
							break;

						}
					}
				}
				if (retVidUrl == null) {

					for (Video video : videos) {
						if (video.ext.toLowerCase().contains("mp4")
								&& video.type.toLowerCase().contains("low")) {
							retVidUrl = video.url;
							break;

						}
					}
				}
				if (retVidUrl == null) {
					for (Video video : videos) {
						if (video.ext.toLowerCase().contains("3gp")
								&& video.type.toLowerCase().contains("low")) {
							retVidUrl = video.url;
							break;
						}
					}
				}
				if (retVidUrl == null) {
					for (Video video : videos) {
						if (video.ext.toLowerCase().contains("flv")
								&& video.type.toLowerCase().contains("low")) {
							retVidUrl = video.url;
							break;
						}
					}
				}
				return retVidUrl;
			}
		} catch (Exception e) {
			Log.e(TAG, "Couldn't get YouTube streaming URL", e);
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(String... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String streamingUrl) {
		super.onPostExecute(streamingUrl);

		if (streamingUrl != null) {
		}
	}
}
