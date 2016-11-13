package com.intocable.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;

public class AsyncTaskHelper extends AsyncTask<Void, Integer, Void> {

	protected Dialog progDailog;
	protected Activity activity = null;
	protected Handler mHandler = new Handler();

	protected long time_limit = 60000;

	/**
	 * @author Micky Ham khoi tao mac dinh neu khong truyen doi so
	 * @param activity
	 *            : activity hien tai
	 */
	public AsyncTaskHelper(Activity activity) {
		this.activity = activity;
	}

	/**
	 * @author Micky Ham duoc goi dau tien khi asytask chay.
	 */
	@Override
	protected void onPreExecute() {
		if (activity == null) {
			return;
		}
		if (activity.isChangingConfigurations()) {
			return;
		}
		// progDailog = ProgressDialog.show(activity, "",
		// "Loading. Please wait...", false);
		mHandler.postDelayed(mUpdateTimeTask, time_limit);
		super.onPreExecute();
	}

	@Override
	protected void onCancelled() {
		if (progDailog != null && progDailog.isShowing())
			progDailog.dismiss();
		mHandler.removeCallbacks(mUpdateTimeTask);
		super.onCancelled();
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		progDailog = ProgressDialog.show(activity, "",
				"Loading. Please wait...", false);
	}

	@Override
	protected void onPostExecute(Void result) {
		if (progDailog != null && progDailog.isShowing())
			progDailog.dismiss();
		mHandler.removeCallbacks(mUpdateTimeTask);

	}

	protected Runnable mUpdateTimeTask = new Runnable() {

		public void run() {
			SetupApp.showMessage(activity,
					"Error occured. Please try again later", "Error");
			if (progDailog != null && progDailog.isShowing())
				progDailog.dismiss();
			mHandler.removeCallbacks(this);
			// mHandler.postDelayed(this, 100);
		}
	};

	public static void close(AsyncTaskHelper asyncTaskHelper) {
		if ((asyncTaskHelper != null) && (!asyncTaskHelper.isCancelled())) {
			asyncTaskHelper.cancel(true);
			asyncTaskHelper = null;
		}
	}

	protected Void doInBackground(String[] Url) {
		// TODO Auto-generated method stub
		return null;
	}

}
