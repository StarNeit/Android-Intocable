package com.intocable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.intocable.R;

public class GCMIntentService extends GCMBaseIntentService {

	/**
	 * Code notification ver 1.1.0 edit code táº¡p trung thÃ nh thÆ°
	 * viá»‡n Code notification ver 1.2.0 Cáº¥u hÃ¬nh code phan tÃ¡ch
	 * pháº§n thÆ° viá»‡n vÃ  pháº§n tÃ¹y biáº¿n cho
	 * tá»«ng dá»± Ã¡n
	 * 
	 * Next task: Code notification ver 1.3.0 ThÃªm thÆ° viá»‡n
	 * hiá»‡n thá»‹ cho notification nhiá»�u hÆ¡n Hiá»‡n
	 * thá»‹ thÃªm má»™t sá»‘ dáº¡ng tÃ¹y biáº¿n
	 * hiá»‡n thá»‹ cho notification nhÆ° media play, notification
	 * picture, notification animation,...
	 **/
	private static final String LOG_TAG = "Hoa debug: GCMIntentService";

	public static final String SENDER_ID = "534885877486";
	public static final String SENDER_KEY = "5583ba04d14de2e2a04c2ff7dcd7f504";
	public static String Registration_Id = "";

	static Activity activity;

	private static int numMessages = 0;

	public GCMIntentService() {
		super(SENDER_ID);
	}

	public static void init(Activity activity) {
		GCMIntentService.activity = activity;
	}

	public String TAG = "";

	@Override
	protected void onRegistered(Context context, String registrationId) {
		// ConfigApp.regid = registrationId;
		Log.i(TAG, "onRegistered: registrationId=" + registrationId);
		Registration_Id = registrationId;
		new SendIdOnOverServer().execute();
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {

		Log.i(TAG, "onUnregistered: registrationId=" + registrationId);
	}

	/**
	 * HÃ m nháº­n notification Ä‘áº§u tiÃªn Lá»�c cÃ¡c tin
	 * nháº¯n theo pack nháº±m Ä‘áº£m báº£o tin nháº¯n
	 * Ä‘Ã³ dÃ¹ng cho app nÃ y Cháº¡y swithCase Ä‘Ã¢y lÃ 
	 * hÃ m tÃ¹y biáº¿n theo app
	 **/
	@Override
	protected void onMessage(Context context, Intent data) {
		Log.i(TAG, "onMessage: Da nhan duoc notification");
		swithCase(data, context);
	}

	/**
	 * Quáº£n táº¯t mÃ n hÃ¬nh sau má»™t khoáº£n thá»�i
	 * gian
	 */
	private void configPowerManager() {
		// Báº­t sÃ¡ng mÃ n hÃ¬nh Android Device khi nháº­n
		// notification
		PowerManager pm = (PowerManager) activity
				.getSystemService(Context.POWER_SERVICE);
		final PowerManager.WakeLock mWakelock = pm.newWakeLock(
				PowerManager.FULL_WAKE_LOCK
						| PowerManager.ACQUIRE_CAUSES_WAKEUP, "GCM_PUSH");
		mWakelock.acquire();

		// Ä�áº·t bá»™ Ä‘áº¿m thá»�i gian Ä‘á»ƒ
		// thiáº¿t bá»‹ Android trá»Ÿ vá»�
		// cháº¿ Ä‘á»™ ngá»§ sau
		// 5000ms.
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				mWakelock.release();
			}
		};
		timer.schedule(task, 5000);

	}

	/**
	 * Notification thÃ´ng bÃ¡o thÃ´ng thÆ°á»�ng cÃ³ 1 icon big
	 * á»Ÿ gÃ³c trÃ¡i láº¥y icon máº·c Ä‘á»‹nh lÃ  icon
	 * app
	 **/
	private void createNotification(Intent data, Context context,
			String s_message, String s_title) {

	}

	private void createNotification_share(Intent data, Context context,
			String s_message, String s_title) {

	}

	/**
	 * Notification thÃ´ng bÃ¡o thÃ´ng thÆ°á»�ng cÃ³ 1 icon big
	 * á»Ÿ gÃ³c trÃ¡i
	 **/
	private void createNotification(String s_message, String s_title,
			int ID_SmallIcon) {
		Intent intent = new Intent();
		PendingIntent pIntent = PendingIntent.getActivity(
				getApplicationContext(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// PendingIntent pIntent = PendingIntent.getActivity(
		// activity, 0, intent, 0);

		Notification notification = new Notification.Builder(
				getApplicationContext()).setSmallIcon(ID_SmallIcon)
				.setWhen(System.currentTimeMillis()).setContentTitle(s_title)
				.setContentText(s_message).setContentIntent(pIntent)
				.getNotification();
		// Loáº¡i bá»� thÃ´ng bÃ¡o notification khi nháº¥p
		// chuá»™t
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// Báº­t Ã¢m thanh thÃ´ng bÃ¡o
		notification.defaults |= Notification.DEFAULT_SOUND;

		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.notify(R.string.app_name, notification);
	}

	/**
	 * Notification thÃ´ng bÃ¡o dáº¡ng Ä‘áº¿m sá»‘ tin
	 * nháº¯n ChÆ°a hoÃ n thÃ nh code
	 **/
	private void createNotificationUpdate(String s_message, String s_title,
			int ID_SmallIcon, int numMessages) {
		NotificationManager managerNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		int notifyID = 1;
		android.support.v4.app.NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
				activity).setContentTitle(s_title)
				.setContentText("You've received new messages.")
				.setSmallIcon(ID_SmallIcon);
		mNotifyBuilder.setSubText(s_message).setNumber(numMessages);
		// mNotifyBuilder.setContentText(s_message);
		managerNotificationManager.notify(notifyID, mNotifyBuilder.build());

	}

	/**
	 * 
	 * Click pushnotification on tabar
	 * 
	 * @param context
	 * @param payload
	 */

	NotificationManager manager;
	Notification myNotication;

	public void createNotification(Context context) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher,
				"Message received", System.currentTimeMillis());
		// Hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// adding LED lights to notification
		notification.defaults |= Notification.DEFAULT_LIGHTS;

		/*
		Intent intent = new Intent("android.intent.action.VIEW",
				Uri.parse("http://my.example.com/"));
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		notification.setLatestEventInfo(context, "Message",
				"New message received", pendingIntent);
		notificationManager.notify(0, notification);*/


		//API level 11
		Intent intent = new Intent("com.rj.notitfications.SECACTIVITY");

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);

		Notification.Builder builder = new Notification.Builder(context);

		builder.setAutoCancel(false);
		builder.setTicker("this is ticker text");
		builder.setContentTitle("WhatsApp Notification");
		builder.setContentText("You have a new message");
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentIntent(pendingIntent);
		builder.setOngoing(true);
		builder.setSubText("This is subtext...");   //API level 16
		builder.setNumber(100);
		builder.build();

		myNotication = builder.getNotification();
		notificationManager.notify(11, myNotication);

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
				String url = "http://www.grupointocable.com/_ws/api/register-android.php?key="
						+ SENDER_KEY
						+ "&gcm="
						+ Registration_Id
						+ "&notification=1";
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

	/****************** Tiáº¿n hÃ nh code tá»« dÆ°á»›i nÃ y ******************************************************************/

	/**
	 * HÃ m tÃ¹y biáº¿n sá»± lÃ½ notification theo tá»«ng app
	 * Hiá»‡n thá»‹ nhÆ° tháº¿ nÃ o? Nháº£y form khi cáº§n
	 * thiáº¿t Tiáº¿n hÃ nh update(Do lá»›p nÃ y cÃ³ thá»ƒ
	 * gá»�i tá»›i má»�i activity, class nÃªn khÃ´ng nÃªn
	 * dÃ¹ng callback á»Ÿ Ä‘Ã¢y vÃ¬ sáº½ rá»‘i ráº¯m
	 * NÃªn kiá»ƒm tra Ä‘iá»�u kiá»‡n tá»“n táº¡i cÃ¡c
	 * biáº¿n activity rá»“i tiáº¿n hÃ nh ep kiá»ƒu dá»¯
	 * liá»‡u
	 **/
	private Boolean swithCase(Intent data, Context context) {
		String message = data.getExtras().getString("message");
		createNotification(message, "Intocable", R.drawable.ic_launcher);

		return true;
	}

	@Override
	protected void onError(Context arg0, String errorId) {
		Log.e(TAG, "onError: errorId=" + errorId);
	}

}