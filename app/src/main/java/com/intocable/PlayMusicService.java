package com.intocable;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;


import com.intocable.utils.CommonVariables;

public class PlayMusicService extends Service {

	private static MediaPlayer mediaPlayer = null;
	String url;

	public static void stopAll() {
		if (mediaPlayer != null) {
			mediaPlayer.reset();
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		if (intent != null && intent.getExtras() != null) {

			url = intent.getExtras().getString(CommonVariables.URL_MUSIC);
			if (url != null && !url.isEmpty())
				startMusic();
			else
				finishMedia();
		}
		return START_STICKY;
	}

	@Override
	public void onTaskRemoved(Intent rootIntent) {
		onDestroy();
		super.onTaskRemoved(rootIntent);
	}

	public void startMusic() {
		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		} else
			mediaPlayer.reset();

		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				finishMedia();
			}
		});

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CommonVariables.BROADCAST_ACTION)
						.putExtra(CommonVariables.ACTION_SERVICE,
								CommonVariables.ACTION_LOAD_MUSIC);
				sendBroadcast(intent);

				try {
					mediaPlayer.setDataSource(url);
					mediaPlayer.prepare();
				} catch (IllegalArgumentException e) {
				} catch (SecurityException e) {
				} catch (IllegalStateException e) {
				} catch (IOException e) {
					e.printStackTrace();
				}
				mediaPlayer.start();

				intent = new Intent(CommonVariables.BROADCAST_ACTION).putExtra(
						CommonVariables.ACTION_SERVICE,
						CommonVariables.ACTION_PLAY_MUSIC);
				sendBroadcast(intent);
			}
		}).start();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	public void finishMedia() {
		Intent intent = new Intent(CommonVariables.BROADCAST_ACTION).putExtra(
				CommonVariables.ACTION_SERVICE,
				CommonVariables.ACTION_FINISH_MUSIC);
		sendBroadcast(intent);
		if (mediaPlayer != null && mediaPlayer.isPlaying())
			mediaPlayer.stop();

		stopSelf();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
