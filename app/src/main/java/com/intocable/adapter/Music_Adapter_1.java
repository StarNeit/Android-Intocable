package com.intocable.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intocable.R;
import com.intocable.Website_Activity;
import com.intocable.app_fragments.Fragment_Music;
import com.intocable.object.Music;
import com.intocable.utils.AppFragment;
import com.intocable.utils.SetupApp;

public class Music_Adapter_1 extends BaseAdapter {

	Activity activity;

	// public static MediaPlayer mPlayer = null;

	public static boolean isPlay = false;
	public static ImageView iv_play_pause = null;
	public static int pre_position = -1;
	public static String urlPlay = "";
	// static ViewGroup view_parrent;

	private LayoutInflater inflater;
	private ArrayList<Music> arrayList;

	private AppFragment _appFragment = null;

	public Music_Adapter_1(Activity activity, ArrayList<Music> arrayList) {
		this.activity = activity;
		this.arrayList = arrayList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public Music_Adapter_1(AppFragment _appFragment, ArrayList<Music> arrayList) {
		this.activity = _appFragment.getActivity();
		this._appFragment = _appFragment;
		this.arrayList = arrayList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	// public void setData(ArrayList<Music> arrayList) {
	// this.arrayList = arrayList;
	// }

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
		// view_parrent = parent;
		final Music item = arrayList.get(position);
		//ViewHolder holder;
		// if (convertView == null || convertView.getTag() == null) {
		convertView = null;
		convertView = inflater.inflate(R.layout.row_music_page, null);

		// holder = new ViewHolder();
		TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
		TextView tvTime = (TextView) convertView.findViewById(R.id.tv_time);
		TextView tvBuy = (TextView) convertView.findViewById(R.id.tv_buy_music);

		ImageView ivPlay = (ImageView) convertView.findViewById(R.id.iv_play);
		ivPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// play music
				if (v.getTag() == null
						|| v.getTag().equals(R.drawable.ic_btn_play)) {
					// stop before music
					// if (mPlayer != null && mPlayer.isPlaying()) {
					// mPlayer.stop();
					// }
					//
					// create_player(position);
					if (iv_play_pause != null) {

						iv_play_pause.setImageResource(R.drawable.ic_btn_play);
						iv_play_pause.setTag(R.drawable.ic_btn_play);
						if (_appFragment != null
								&& _appFragment instanceof Fragment_Music) {
							((Fragment_Music) _appFragment).stopMusic();
						}

					}
					((ImageView) v).setImageResource(R.drawable.ic_btn_pause);
					v.setTag(R.drawable.ic_btn_pause);
					iv_play_pause = (ImageView) v;

					pre_position = position;
					urlPlay = item.getPlay();
					if (_appFragment != null
							&& _appFragment instanceof Fragment_Music) {
						((Fragment_Music) _appFragment).playMusic(urlPlay);
					}

				}

				// pause music
				else {
					// stop music
					((ImageView) v).setImageResource(R.drawable.ic_btn_play);
					v.setTag(R.drawable.ic_btn_play);
					if (_appFragment != null
							&& _appFragment instanceof Fragment_Music) {
						((Fragment_Music) _appFragment).stopMusic();
					}
					iv_play_pause = null;
				}
			}
		});
		// convertView.setTag(holder);
		// } else {
		// holder = (ViewHolder) convertView.getTag();
		// }

		tvName.setText(item.getTitle());
		/*
		 * if (position == pre_position && isPlay) {
		 * holder.ivPlay.setImageResource(R.drawable.ic_btn_pause);
		 * holder.ivPlay.setTag(R.drawable.ic_btn_pause); //iv_play_pause =
		 * holder.ivPlay; } else {
		 * holder.ivPlay.setImageResource(R.drawable.ic_btn_play);
		 * holder.ivPlay.setTag(R.drawable.ic_btn_play); }
		 */

		if (iv_play_pause != null) {
			iv_play_pause.setImageResource(R.drawable.ic_btn_pause);
			iv_play_pause.setTag(R.drawable.ic_btn_pause);
		} 

		tvBuy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isPlay)
					if (_appFragment != null
							&& _appFragment instanceof Fragment_Music) {
						((Fragment_Music) _appFragment).stopMusic();
					}
				Website_Activity.url = item.getBuy();
				SetupApp.gotoActivity(activity, Website_Activity.class);
			}
		});

		// }
		// view_parrent = parent;
		// final ViewGroup view_parrent = parent;
		// if (convertView == null) {
		//
		// convertView = inflater.inflate(R.layout.row_music_page, null);
		//
		// }

		// TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		// TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
		// TextView tv_buy_music = (TextView) convertView
		// .findViewById(R.id.tv_buy_music);
		// tv_name.setText(item.getTitle());
		// iv_play_pause = (ImageView) convertView.findViewById(R.id.iv_play);
		// iv_play_pause.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// for (int i = 0; i < view_parrent.getChildCount(); i++) {
		// View view_child = view_parrent.getChildAt(i);
		// ImageView image_view = (ImageView) view_child
		// .findViewById(R.id.iv_play);
		// image_view.setImageResource(R.drawable.ic_btn_play);
		// Log.i("Music", "====>pause " + position);
		// if (mPlayer == null) {
		//
		// } else {
		// mPlayer.stop();
		// }
		// }
		//
		// if (isPlay) {
		// create_player(position, item);
		// mPlayer.start();
		// ((ImageView) v).setImageResource(R.drawable.ic_btn_pause);
		// isPlay = false;
		// } else {
		// isPlay = true;
		//
		// if (pre_position != position) {
		// create_player(position, item);
		// mPlayer.start();
		// ((ImageView) v)
		// .setImageResource(R.drawable.ic_btn_pause);
		// }
		//
		// }
		// pre_position = position;
		//
		// }
		//
		// });
		//
		// // ------------------------------------------------
		// tv_buy_music.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// Website_Activity.url = item.getBuy();
		// SetupApp.gotoActivity(activity, Website_Activity.class);
		// }
		// });

		return convertView;

	}

	// public int getPlayPosition() {
	// return pre_position;
	// }
	//
	// public void replay() {
	// if (pre_position == -1)
	// return;
	//
	// // set play button
	// if (iv_play_pause != null) {
	// iv_play_pause.setImageResource(R.drawable.ic_btn_pause);
	// iv_play_pause.setTag(R.drawable.ic_btn_pause);
	// }
	//
	// if (mPlayer != null && mPlayer.isPlaying()) {
	// return;
	// }
	//
	// if (mPlayer == null) {
	// mPlayer = new MediaPlayer();
	// mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	// } else
	// mPlayer.reset();
	//
	// final String url = arrayList.get(pre_position).getPlay();
	// Log.i("Log url", "===>" + url);
	//
	// dialog = ProgressDialog.show(activity, "", "Buffering ...");
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// try {
	// mPlayer.setDataSource(url);
	// mPlayer.prepare();
	// } catch (IllegalArgumentException e) {
	// } catch (SecurityException e) {
	// } catch (IllegalStateException e) {
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// activity.runOnUiThread(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// if (dialog != null && dialog.isShowing())
	// dialog.dismiss();
	//
	// mPlayer.start();
	// if (activity instanceof Music_Activity)
	// ((Music_Activity) activity).resumeLand();
	// }
	// });
	//
	// }
	// }).start();
	//
	// checkPlayer();
	// }
	//
	// public void resume() {
	// if (mPlayer != null && mPlayer.isPlaying())
	// if (iv_play_pause != null) {
	// iv_play_pause.setImageResource(R.drawable.ic_btn_pause);
	// iv_play_pause.setTag(R.drawable.ic_btn_pause);
	// }
	// }
	//
	// public void create_player(int pos) {
	// // TODO Auto-generated method stub
	//
	// // set play button
	// if (iv_play_pause != null) {
	// iv_play_pause.setImageResource(R.drawable.ic_btn_play);
	// iv_play_pause.setTag(R.drawable.ic_btn_play);
	// }
	//
	// final String url = arrayList.get(pos).getPlay();
	// Log.i("Log url", "===>" + url);
	//
	// if (mPlayer == null) {
	// mPlayer = new MediaPlayer();
	// mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	// } else
	// mPlayer.reset();
	//
	// dialog = ProgressDialog.show(activity, "", "Buffering ...");
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// try {
	// mPlayer.setDataSource(url);
	// mPlayer.prepare();
	// } catch (IllegalArgumentException e) {
	// } catch (SecurityException e) {
	// } catch (IllegalStateException e) {
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// activity.runOnUiThread(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// if (dialog != null && dialog.isShowing())
	// dialog.dismiss();
	// mPlayer.start();
	// if (activity instanceof Music_Activity)
	// ((Music_Activity) activity).resumeLand();
	// }
	// });
	//
	// }
	// }).start();
	//
	// checkPlayer();
	// }
	//
	// public static void stop() {
	// // TODO Auto-generated method stub
	// if (mPlayer == null) {
	//
	// } else {
	// mPlayer.stop();
	// if (iv_play_pause != null) {
	// iv_play_pause.setImageResource(R.drawable.ic_btn_play);
	// iv_play_pause.setTag(R.drawable.ic_btn_play);
	// }
	// }
	// }
	//
	// public static void pause() {
	// // TODO Auto-generated method stub
	// if (mPlayer == null) {
	//
	// } else {
	// mPlayer.pause();
	// }
	// }
	//
	// public static void repause() {
	// // TODO Auto-generated method stub
	// if (mPlayer == null) {
	//
	// } else {
	// mPlayer.start();
	// }
	// }
	//
	// public static void checkPlayer() {
	// // TODO Auto-generated method stub
	// mPlayer.setOnCompletionListener(new OnCompletionListener() {
	// @Override
	// public void onCompletion(MediaPlayer mp) {
	// Log.i("Stop=====>", "your code comes here");
	// if (iv_play_pause != null) {
	// iv_play_pause.setImageResource(R.drawable.ic_btn_play);
	// iv_play_pause.setTag(R.drawable.ic_btn_play);
	// }
	//
	// if (activity instanceof Music_Activity)
	// ((Music_Activity) activity).resumeLand();
	// // for (int i = 0; i < view_parrent.getChildCount(); i++) {
	// // View view_child = view_parrent.getChildAt(i);
	// // ImageView image_view = (ImageView) view_child
	// // .findViewById(R.id.iv_play);
	// // image_view.setImageResource(R.drawable.ic_btn_play);
	// // image_view.setTag(R.drawable.ic_btn_play);
	// // }
	// }
	// });
	//
	// }

	class ViewHolder {
		TextView tvName;
		TextView tvTime;
		TextView tvBuy;
	}
}
