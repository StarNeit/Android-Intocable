package com.intocable.adapter;

import java.io.IOException;
import java.util.ArrayList;

import com.intocable.R;
import com.intocable.Website_Activity;
import com.intocable.object.Music;
import com.intocable.utils.SetupApp;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Music_Adapter extends BaseAdapter implements OnClickListener {

	public Activity activity;
	public ArrayList<Music> arrayList;
	static MediaPlayer mPlayer;
	private CheckBox ck_play_pause_music;
	private String url_play;
	boolean isPlay = false;
	private ImageButton iv_play;

	public Music_Adapter(Activity activity, ArrayList<Music> arrayList) {
		this.activity = activity;
		this.arrayList = arrayList;
	}

	public void setData(ArrayList<Music> arrayList) {
		this.arrayList = arrayList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Music item = arrayList.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_music_page, null);

		}

		

		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
		TextView tv_buy_music = (TextView) convertView
				.findViewById(R.id.tv_buy_music);
		iv_play = (ImageButton) convertView.findViewById(R.id.iv_play);
		iv_play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isPlay) {
					iv_play.setBackgroundResource(R.drawable.ic_btn_pause);
				} else {
					iv_play.setBackgroundResource(R.drawable.ic_btn_play);
				}

				isPlay = !isPlay; // reverse
			}
		});

		ck_play_pause_music.setOnClickListener(this);

		tv_name.setText(item.getTitle());
		
		url_play = item.getPlay();
		create_player();
		tv_time.setText(""+mPlayer.getDuration());
		
		tv_buy_music.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Website_Activity.url = item.getBuy();
				SetupApp.gotoActivity(activity, Website_Activity.class);
			}
		});

		return convertView;

	}

	private void create_player() {
		// TODO Auto-generated method stub
		mPlayer = new MediaPlayer();
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mPlayer.setDataSource(url_play);
		} catch (IllegalArgumentException e) {
		} catch (SecurityException e) {
		} catch (IllegalStateException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			mPlayer.prepare();
		} catch (IllegalStateException e) {
		} catch (IOException e) {
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		

		// case R.id.iv_pause_music:
		// Log.i("Log click", "====> stop");
		// mPlayer.stop();
		// break;

		
	}

	public static void stop() {
		// TODO Auto-generated method stub
		mPlayer.stop();

	}

}
