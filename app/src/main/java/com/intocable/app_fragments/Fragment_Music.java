package com.intocable.app_fragments;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.intocable.MainHome_Activity;
import com.intocable.PlayMusicService;
import com.intocable.R;
import com.intocable.adapter.Music_Adapter_1;
import com.intocable.object.Music;
import com.intocable.utils.AppFragment;
import com.intocable.utils.CommonMethods;
import com.intocable.utils.CommonVariables;
import com.intocable.utils.SetupApp;
import com.intocable.ws.GetXmlWeb;
import com.intocable.ws.OnResultAsyncTask;
import com.intocable.ws.WsIntoCable;

public class Fragment_Music extends AppFragment implements OnResultAsyncTask {

	private ImageView iv_timeline_music;

	private ImageView ivPlayLand = null;
	private TextView tvPlayLand = null;

	public static String linkBanner = "";

	private ListView lv_music_page;
	private Music_Adapter_1 adapter;

	private String url;

	public static GetXmlWeb xmlGetter = null;

	private MusicReceiver receiver;

	private Intent playServiceIntent;

	private AQuery mAQuery;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_music_page, null);
		SetupApp.setHeader(getActivity().getString(R.string.header_music),
				getActivity());
		receiver = new MusicReceiver();
		mAQuery = new AQuery(getActivity());
		initmain(view);
		initData();
		resumeLand();
		return view;
	}

	public void playMusic(String url) {

		if (playServiceIntent != null)
			getActivity().stopService(playServiceIntent);
		playServiceIntent = new Intent(getActivity(), PlayMusicService.class);
		playServiceIntent.putExtra(CommonVariables.URL_MUSIC, url);
		getActivity().startService(playServiceIntent);

		Music_Adapter_1.isPlay = true;

		resumeLand();
	}

	@Override
	public void onDestroy() {
		stopMusic();
		// Music_Adapter_1.iv_play_pause = null;
		Music_Adapter_1.pre_position = -1;
		Music_Adapter_1.urlPlay = "";
		super.onDestroy();
	}

	public void stopMusic() {
		if (playServiceIntent != null) {
			PlayMusicService.stopAll();
			getActivity().stopService(playServiceIntent);
			// playServiceIntent = null;

		}
		Music_Adapter_1.isPlay = false;

		// if (Music_Adapter_1.iv_play_pause != null) {
		// Music_Adapter_1.iv_play_pause
		// .setImageResource(R.drawable.ic_btn_play);
		// Music_Adapter_1.iv_play_pause.setTag(R.drawable.ic_btn_play);
		// }

		resumeLand();
	}

	@Override
	public void onPause() {
		// coment cho nay lai khong hieu sao lai gay loi o day! se kiem tra sau:
//		if (receiver != null) {
//			getActivity().unregisterReceiver(receiver);
//		}
		super.onPause();
	}

	public void onResume() {
		super.onResume();
		if (receiver == null) {
			receiver = new MusicReceiver();
			getActivity().registerReceiver(receiver,
					new IntentFilter(CommonVariables.BROADCAST_ACTION));

		}

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		stopMusic();
		// Music_Adapter_1.iv_play_pause = null;
		Music_Adapter_1.pre_position = -1;
		Music_Adapter_1.urlPlay = "";
		super.finish();
	}

	public void resumeLand() {
		if (ivPlayLand != null && tvPlayLand != null) {
			if (Music_Adapter_1.pre_position != -1) {
				ivPlayLand.setVisibility(View.VISIBLE);
				tvPlayLand.setVisibility(View.VISIBLE);

				tvPlayLand.setText(CommonVariables.musics.get(
						Music_Adapter_1.pre_position).getTitle());

				if (!Music_Adapter_1.isPlay) {
					ivPlayLand.setImageResource(R.drawable.ic_btn_play);
				} else {
					ivPlayLand.setImageResource(R.drawable.ic_btn_pause);
				}
			} else {
				ivPlayLand.setVisibility(View.GONE);
				tvPlayLand.setVisibility(View.GONE);
			}

		}
	}

	private void initmain(View view) {
		// TODO Auto-generated method stub
		getActivity().registerReceiver(receiver,
				new IntentFilter(CommonVariables.BROADCAST_ACTION));

		lv_music_page = (ListView) view.findViewById(R.id.lv_music_page);
		iv_timeline_music = (ImageView) view
				.findViewById(R.id.iv_timeline_music);

		ivPlayLand = (ImageView) view.findViewById(R.id.iv_play_land);
		tvPlayLand = (TextView) view.findViewById(R.id.tv_name_land);

		if (ivPlayLand != null) {
			ivPlayLand.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (Music_Adapter_1.isPlay) {
						stopMusic();
						ivPlayLand.setImageResource(R.drawable.ic_btn_play);
					} else {
						playMusic(Music_Adapter_1.urlPlay);
						ivPlayLand.setImageResource(R.drawable.ic_btn_pause);
					}
				}
			});
		}
	}

	private void initData() {
		// TODO Auto-generated method stub

		if (CommonVariables.musics == null)
			CommonVariables.musics = new ArrayList<Music>();

		adapter = new Music_Adapter_1(this, CommonVariables.musics);
		lv_music_page.setAdapter(adapter);
		SetupApp.setListViewHeightBasedOnChildren(lv_music_page);

		if (!linkBanner.isEmpty())
			mAQuery.id(iv_timeline_music).image(linkBanner, false, true, 200,
					0, null, AQuery.FADE_IN);

		url = WsIntoCable.getLink(WsIntoCable.TYPE_MUSIC);
		getData();
		// new MusicAsyncTask().execute(url);

	}

	private void getData() {
		MainHome_Activity.showLoading();
		if (xmlGetter != null && !xmlGetter.isCancelled())
			xmlGetter.cancel(true);

		xmlGetter = new GetXmlWeb(this, GetXmlWeb.FOREGROUND,
				GetXmlWeb.TYPE_MUSIC);
		xmlGetter.execute();
	}

	@Override
	public void showResult(JSONObject result) {
		// TODO Auto-generated method stub

	}

	public void install_UniversalImageLoader() {

	}

	@Override
	public void showResult(String xml) {
		// TODO Auto-generated method stub
		// result from asynctask

		MainHome_Activity.hideLoading();
		if (xml != null && !xml.isEmpty()) {

			// parser
			CommonMethods.getMusic(url, xml);
			Log.d("LAN DEBUG", linkBanner);
			if (!linkBanner.isEmpty()) {
				mAQuery.clear();
				mAQuery.id(iv_timeline_music).image(linkBanner, false, false,
						200, 0, null, AQuery.FADE_IN);
			} else
				mAQuery.id(iv_timeline_music)
						.image("http://www.grupointocable.com/_ws/images/Music/Music.jpg",
								false, true, 200, 0, null, AQuery.FADE_IN);

			while (Music_Adapter_1.isPlay)
				;
			adapter.notifyDataSetChanged();
			SetupApp.setListViewHeightBasedOnChildren(lv_music_page);

		}
	}

	private class MusicReceiver extends BroadcastReceiver {

		// Called when the BroadcastReceiver gets an Intent it's registered to
		// receive
		public void onReceive(Context context, Intent intent) {
			String Status = intent
					.getStringExtra(CommonVariables.ACTION_SERVICE);
			if (Status.equalsIgnoreCase(CommonVariables.ACTION_LOAD_MUSIC)) {

				MainHome_Activity.showLoading();
			} else if (Status
					.equalsIgnoreCase(CommonVariables.ACTION_PLAY_MUSIC)) {

				MainHome_Activity.hideLoading();

			} else if (Status
					.equalsIgnoreCase(CommonVariables.ACTION_FINISH_MUSIC)) {

				stopMusic();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

}
