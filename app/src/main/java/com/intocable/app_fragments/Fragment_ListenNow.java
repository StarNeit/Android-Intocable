package com.intocable.app_fragments;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.androidquery.AQuery;
import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.utils.AppFragment;
import com.intocable.utils.CommonVariables;
import com.intocable.utils.ConfigApp;
import com.intocable.utils.GetStreamingUrl;
import com.intocable.utils.SetupApp;
import com.intocable.ws.GetXmlWeb;

public class Fragment_ListenNow extends AppFragment {
	private static ImageView iv_play_listen_now;
	private ImageView ivBackground = null;
	static MediaPlayer mediaPlayer = null;
	private SeekBar seekbar;
	private LinearLayout ll_listen_now;
	private TextView tv_title_listen_now;
	public static String url_mp3 = "";
	private String background;
	private boolean isPlay = true;
	private AudioManager audioManager = null;
	private Bitmap bitmap;
	protected Handler mHandler = new Handler();
	protected long time_limit = 10000;

	public static GetXmlWeb xmlGetter = null;
	private AQuery mAQuery;
	int width;
	int height;
	String url_background = null;
	Element element = null;
	LinkedList<String> urls;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_listen_now, null);
		mAQuery = new AQuery(getActivity());
		initmain(view);

		initData();
		initControls();
		return view;
	}

	private void initControls() {
		// TODO Auto-generated method stub
		try {
			audioManager = (AudioManager) getActivity().getSystemService(
					Context.AUDIO_SERVICE);
			seekbar.setMax(audioManager
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
			seekbar.setProgress(audioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC));

			seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				@Override
				public void onStopTrackingTouch(SeekBar arg0) {
				}

				@Override
				public void onStartTrackingTouch(SeekBar arg0) {
				}

				@Override
				public void onProgressChanged(SeekBar arg0, int progress,
						boolean arg2) {
					audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
							progress, 0);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void checkPlayer() {
		// TODO Auto-generated method stub
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				iv_play_listen_now
						.setBackgroundResource(R.drawable.ic_btn_play);
			}
		});
	}

	private void initmain(View view) {
		// TODO Auto-generated method stub
		iv_play_listen_now = (ImageView) view
				.findViewById(R.id.iv_play_listen_now);
		ll_listen_now = (LinearLayout) view.findViewById(R.id.ll_listen_now);
		tv_title_listen_now = (TextView) view
				.findViewById(R.id.tv_title_listen_now);
		seekbar = (SeekBar) view.findViewById(R.id.seekBar_listen_now);
		ivBackground = (ImageView) view.findViewById(R.id.imageView1);
		update_view();
	}

	void show() {
		MainHome_Activity.showLoading();
	}

	void dismiss() {
		MainHome_Activity.hideLoading();
	}

	private LinkedList<String> fGetPlayableUrl(String mPls) {
		GetStreamingUrl oGetStreamingUrl = new GetStreamingUrl(getActivity());
		urls = oGetStreamingUrl.getStreamingUrl(mPls);
		return urls;
	}

	private void initData() {
		// TODO Auto-generated method stub
		String url = ConfigApp.link_ws("listen");
		if (SetupApp.checkInternetConnection(getActivity()))
			if (element == null) {
				show();
				new Listen_Now_AsyncTask().execute(url);
			}
		// creat_media(url_mp3);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		switch (id) {
		case R.id.iv_play_listen_now:
			if (isPlay) {
				play();
				iv_play_listen_now
						.setBackgroundResource(R.drawable.ic_btn_pause);
				isPlay = false;
			} else {
				isPlay = true;
				mediaPlayer.pause();
				iv_play_listen_now
						.setBackgroundResource(R.drawable.ic_btn_play);
			}
			break;

		case R.id.tv_header_buy:
			break;
		default:
			break;
		}
	}

	void update_view() {
		if (mediaPlayer == null)
			iv_play_listen_now.setBackgroundResource(R.drawable.ic_btn_play);
		else
			iv_play_listen_now.setBackgroundResource(R.drawable.ic_btn_pause);

		if (element != null) {
			tv_title_listen_now.setText(element.getAttribute("Title"));
			url_background = element.getAttribute("Background");
			url_mp3 = element.getAttribute("StationURL");
			background = element.getAttribute("Background");
			mAQuery.id(R.id.imageView1).image(background);

		}
	}

	private void play() {
		// TODO Auto-generated method stub
		if (mediaPlayer == null)
			creat_media(url_mp3);
		else
			mediaPlayer.start();

	}

	public static void stop() {
		// TODO Auto-generated method stub
		if (mediaPlayer != null && mediaPlayer.isPlaying())
			mediaPlayer.stop();

	}

	@Override
	public void onDestroy() {
		// if (mediaPlayer != null && mediaPlayer.isPlaying())
		// mediaPlayer.stop();
		// mediaPlayer = null;
		super.onDestroy();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		if (mediaPlayer != null && mediaPlayer.isPlaying())
			mediaPlayer.stop();
		mediaPlayer = null;

		super.finish();
	}

	// ------------------------- parse xml-------------------
	private class Listen_Now_AsyncTask extends AsyncTask<String, Void, Void> {
		ProgressDialog pDialog;
		NodeList nodelist;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressbar
			pDialog = new ProgressDialog(getActivity());

			super.onPreExecute();
		}

		protected Runnable mUpdateTimeTask = new Runnable() {

			public void run() {
				SetupApp.showMessage(getActivity(),
						"Error occured. Please try again later", "Error");
				pDialog.dismiss();
				mHandler.removeCallbacks(this);
				// mHandler.postDelayed(this, 100);
			}
		};

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
				nodelist = doc.getElementsByTagName("Station");

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(Void args) {
			if (nodelist != null) {
				for (int temp = 0; temp < nodelist.getLength(); temp++) {
					Node nNode = nodelist.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						element = (Element) nodelist.item(temp);
					}
				}
				update_view();
				dismiss();
			}

		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

	private void creat_media(String url_mp3) {
		// TODO Auto-generated method stub
		MainHome_Activity.showLoading();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				LinkedList<String> url = fGetPlayableUrl(Fragment_ListenNow.url_mp3);
				if (url != null)
					Fragment_ListenNow.url_mp3 = url.getFirst();
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				try {
					mediaPlayer.setDataSource(Fragment_ListenNow.url_mp3);
					// mediaPlayer.setDataSource("http://s5.myradiostream.com:5102/");
				} catch (IllegalArgumentException e) {
				} catch (SecurityException e) {
				} catch (IllegalStateException e) {
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					mediaPlayer.prepare();
				} catch (IllegalStateException e) {
				} catch (IOException e) {
				}
				checkPlayer();
				if (mediaPlayer != null)
					mediaPlayer.start();
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							MainHome_Activity.hideLoading();
						}
					});
				}
			}
		}).start();

	}

	// getNode function
	private static String getNode(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

}
