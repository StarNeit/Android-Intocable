package com.intocable.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;

import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.intocable.R;

public class SetupApp {

	private static final String LOG_TAG = "Hoa debug: SetupApp";
	public static final int OPEN_WIRELESS = 100;
	public static Activity last_Activity = null;

	public static void openWileressSetting(Activity activity) {
		Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
		activity.startActivityForResult(intent, OPEN_WIRELESS);
	}

	public static Date getStartDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Check app is installed
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isAppInstalled(Context context, String packageName) {
		Intent mIntent = context.getPackageManager().getLaunchIntentForPackage(
				packageName);
		if (mIntent != null) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkLinkYoutube(String link) {
		String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

		Pattern compiledPattern = Pattern.compile(pattern,
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = compiledPattern.matcher(link);
		return matcher.matches();
	}

	/**
	 * Get text of Edittext, TextView or Button
	 * 
	 * @param id
	 *            id of component
	 * @param activity
	 * @return
	 */
	public static String getText(int id, final Activity activity) {
		View view = activity.findViewById(id);
		try {
			return ((EditText) view).getText().toString();
		} catch (Exception e) {

		}

		try {
			return ((TextView) view).getText().toString();
		} catch (Exception e) {

		}

		try {
			return ((Button) view).getText().toString();
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * Set text for TextView, EditText, Button
	 * 
	 * @param id
	 * @param text
	 * @param activity
	 */
	public static void setText(int id, String text, final Activity activity) {
		View view = activity.findViewById(id);
		try {
			((EditText) view).setText(text);
			return;
		} catch (Exception e) {
		}
		try {
			((TextView) view).setText(text);
			return;
		} catch (Exception e) {
		}
		try {
			((Button) view).setText(text);
			return;
		} catch (Exception e) {
		}
	}

	public static void setComponentEnable(int id, boolean isEnable,
			final Activity activity) {
		View view = activity.findViewById(id);
		view.setEnabled(isEnable);
	}

	public static void setTextViewColor(int id, String colorHex,
			final Activity activity) {
		View view = activity.findViewById(id);
		try {
			((TextView) view).setTextColor(Color.parseColor(colorHex));
		} catch (Exception e) {

		}
	}

	/**
	 * Set background resource for component
	 * 
	 * @param id
	 * @param resource
	 * @param activity
	 */
	public static void setBackgroundResource(int id, int resource,
			final Activity activity) {
		View view = activity.findViewById(id);
		view.setBackgroundResource(resource);
	}

	public static void setVisibility(int id, int visibility, Activity activity) {
		View view = activity.findViewById(id);
		view.setVisibility(visibility);
	}

	public static void hiddenKeyboard(Activity activity) {
		InputMethodManager inputManager = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static void setVisibilityHeader(int visibility,
			final Activity activity) {

		View header = activity.findViewById(R.id.header);
		header.setVisibility(visibility);
	}

	public static void setHeader(String tittle, final Activity activity) {
		SetupApp.setText(R.id.tv_title, tittle, activity);
		TextView tv_header_buy = (TextView) activity
				.findViewById(R.id.tv_header_buy);
		// if (!(activity instanceof Listen_now_Activity)) {
		// tv_header_buy.setVisibility(View.GONE);
		// } else {
		// tv_header_buy.setVisibility(View.VISIBLE);
		// }
	}

	public static void setHeader(int id_tittle, final Activity activity) {
		String tittle = activity.getString(id_tittle);
		setHeader(tittle, activity);
	}

	public static void setupUI(View view, final Activity activity) {

		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText) || view.isFocusable() == false) {

			view.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					try {
						hideSoftKeyboard(activity);
					} catch (Exception e) {
						// TODO: handle exception
					}

					return false;
				}

			});
		}
		if (view instanceof ViewGroup) {

			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

				View innerView = ((ViewGroup) view).getChildAt(i);

				setupUI(innerView, activity);
			}
		}
	}

	private static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
				.getWindowToken(), 0);
	}

	public static void gotoActivity(Activity activity, Class<?> cls,
			boolean isFinish, Bundle bundle) {
		gotoActivity(activity, cls, isFinish, bundle, false);
	}

	public static void gotoActivity(Activity activity, Class<?> cls,
			boolean isFinish, Bundle bundle, Boolean isTop) {
		Intent riIntent = new Intent(activity, cls);
		if (isTop) {
			riIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);

		} else
			last_Activity = activity;
		if (bundle != null)
			riIntent.putExtras(bundle);
		activity.startActivity(riIntent);
		if (isFinish)
			activity.finish();
		// activity.overridePendingTransition(R.anim.in_from_right,
		// R.anim.out_to_left);
	}

	public static void gotoActivity(Activity activity, Class<?> cls,
			boolean isFinish, Boolean isTop) {
		gotoActivity(activity, cls, isFinish, null, isTop);
	}

	public static void gotoActivity(Activity activity, Class<?> cls,
			boolean isFinish) {
		gotoActivity(activity, cls, isFinish, null, false);
	}

	public static void gotoActivity(Activity activity, Class<?> cls) {
		gotoActivity(activity, cls, false);
	}

	public static void gotoActivity(Activity activity, Class<?> cls,
			Bundle bundle) {
		gotoActivity(activity, cls, false, bundle, false);
	}

	public static void callPhone(Activity activity, String tel) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.DIAL");
		intent.setData(Uri.parse("tel:" + tel));
		activity.startActivity(intent);
	}

	public static void Select_file(Activity activity, int requestCode) {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		activity.startActivityForResult(photoPickerIntent, requestCode);
	}

	public static Uri openCamera(Activity activity, int requestCode) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		File photo = new File(Environment.getExternalStorageDirectory(),
				"Pic.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		Uri imageUri = Uri.fromFile(photo);
		activity.startActivityForResult(intent, requestCode);
		return imageUri;
	}

	public static String getUserPhoneNumber(Activity activity) {
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager) activity
				.getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNumber = mTelephonyMgr.getLine1Number();
		if (phoneNumber != null && phoneNumber.length() > 2) {
			return phoneNumber.substring(2);
		}
		return "";
	}

	// public static void configSpinner(Activity activity, int id_spinner,
	// int id_arr) {
	// configSpinner(activity, id_spinner, id_arr, null);
	// }
	//
	// public static void configSpinner(Activity activity, int id_spinner,
	// int id_arr, final SpinerEventSelected even) {
	// Resources res = activity.getResources();
	// String[] str_explosions = res.getStringArray(id_arr);
	//
	// final Spinner spinner = (Spinner) activity.findViewById(id_spinner);
	// if (spinner == null)
	// return;
	// ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	// activity, id_arr, android.R.layout.simple_spinner_item);
	// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// spinner.setAdapter(adapter);
	// if (spinner.getCount() != 0)
	// spinner.setTag(1);
	// else
	// spinner.setTag(-1);
	//
	// spinner.setOnItemSelectedListener(new
	// AdapterView.OnItemSelectedListener() {
	//
	// @Override
	// public void onItemSelected(AdapterView<?> parent, View view,
	// int pos, long id) {
	// spinner.setTag(pos + 1);
	// if (even != null)
	// even.selectIemIndex(pos);
	// }
	//
	// @Override
	// public void onNothingSelected(AdapterView<?> arg0) {
	// if (spinner.getCount() != 0)
	// spinner.setTag(1);
	// else
	// spinner.setTag(-1);
	// }
	// });
	//
	// }

	public static String getRealPathFromURI(Activity activity, Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		CursorLoader loader = new CursorLoader(activity, contentUri, proj,
				null, null, null);
		Cursor cursor = loader.loadInBackground();
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public static int getTagInt(int id, final Activity activity) {
		View view = activity.findViewById(id);
		if (view.getTag() == null)
			return -1;
		return Integer.parseInt(view.getTag().toString());
	}

	public static void format_edit_text_input(int id, final Activity activity) {
		View view = activity.findViewById(id);
		if (!(view instanceof EditText))
			return;
		final int[] n_number = { 2, 2, 3, 3, 3, 3 };
		final Pattern CODE_PATTERN = Pattern
				.compile("([0-9]{0,2})|([0-9]{0,2}-)+|[0-9]{0,2}(-[0-9]{2,3})+");

		final EditText editText = (EditText) view;
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() > 0) {
					String input = s.toString();
					String numbersOnly = keepNumbersOnly(input);
					String code = formatNumbersAsCode(numbersOnly);

					editText.removeTextChangedListener(this);
					editText.setText(code);
					editText.setSelection(code.length());
					editText.addTextChangedListener(this);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			private String keepNumbersOnly(CharSequence s) {
				return s.toString().replaceAll("[^0-9]", ""); // Should of
																// course be
																// more robust
			}

			private String formatNumbersAsCode(CharSequence s) {
				int groupDigits = 0;
				int index = 0;
				String tmp = "";
				for (int i = 0; i < s.length(); ++i) {
					if (index == n_number.length)
						return tmp;

					tmp += s.charAt(i);
					++groupDigits;
					if (groupDigits == n_number[index]) {
						if (index < n_number.length - 1)
							tmp += "-";
						groupDigits = 0;
						index++;

					}
				}
				return tmp;
			}

		});

	}

	public static int dimen(final Activity activity, int id_dimen) {
		return (int) activity.getResources().getDimension(id_dimen);
	}

	public static String formatDateTime(String s_DateTime,
			final Activity activity) {
		SimpleDateFormat fm_time_date_in = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		TimeZone tz_server = TimeZone.getTimeZone("GMT-7");
		fm_time_date_in.setTimeZone(tz_server);

		SimpleDateFormat fm_time_today = new SimpleDateFormat("h:mm a",
				Locale.ENGLISH);
		SimpleDateFormat fm_time_date = new SimpleDateFormat("MMM dd",
				Locale.ENGLISH);
		SimpleDateFormat dateOnly = new SimpleDateFormat("ddMMyyyy");
		Date dtoday = new Date();
		String s_StartDate;
		if (s_DateTime.length() == 0)
			s_StartDate = fm_time_today.format(dtoday);

		Date d_DateTime = null;
		try {
			d_DateTime = fm_time_date_in.parse(s_DateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String stoday = dateOnly.format(dtoday);
		String sDateTime = dateOnly.format(d_DateTime);

		if (stoday.equals(sDateTime)) {
			s_StartDate = fm_time_today.format(d_DateTime);
		} else {
			s_StartDate = fm_time_date.format(d_DateTime);
		}
		return s_StartDate;
	}

	public static String formatDateTime2(String s_DateTime,
			final Activity activity) {
		SimpleDateFormat fm_time_date_in = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		TimeZone tz_server = TimeZone.getTimeZone("GMT-7");
		fm_time_date_in.setTimeZone(tz_server);

		SimpleDateFormat fm_time_out = new SimpleDateFormat(
				"dd MMM yyyy  h:mm a", Locale.ENGLISH);

		Date dtoday = new Date();
		String s_StartDate;
		if (s_DateTime.length() == 0)
			s_StartDate = fm_time_out.format(dtoday);

		Date d_DateTime = null;
		try {
			d_DateTime = fm_time_date_in.parse(s_DateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s_StartDate = fm_time_out.format(d_DateTime);

		return s_StartDate;
	}

	public static String formatDateTime3(long milliSeconds,
			final Activity activity) {
		SimpleDateFormat fm_time_out = new SimpleDateFormat("dd/MM");
		String s_StartDate = fm_time_out.format(new Date(milliSeconds * 1000));
		return s_StartDate;
	}

	public static String formatDateTime4(String s_DateTime,
			final Activity activity) {
		SimpleDateFormat fm_time_date_in = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		TimeZone tz_server = TimeZone.getTimeZone("GMT-7");
		fm_time_date_in.setTimeZone(tz_server);

		SimpleDateFormat fm_time_out = new SimpleDateFormat("h:mm a  dd MMM",
				Locale.ENGLISH);

		Date dtoday = new Date();
		String s_StartDate;
		if (s_DateTime.length() == 0)
			s_StartDate = fm_time_out.format(dtoday);

		Date d_DateTime = null;
		try {
			d_DateTime = fm_time_date_in.parse(s_DateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s_StartDate = fm_time_out.format(d_DateTime);

		return s_StartDate;
	}

	public static String formatFloat(Double d_input) {
		return String.format("%.1f", d_input);
	}

	public static String getEmail(Activity activity) {
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(activity).getAccounts();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				String possibleEmail = account.name;
				return possibleEmail;
			}
		}
		return null;
	}

	public static void showMessage(final Activity activity, String msg,
			String Title) {
		if (activity != null)
			new AlertDialog.Builder(activity).setTitle(Title).setMessage(msg)
					.setNeutralButton("Close", null).show();
	}

	public static void showMessageOk(Context context, String title,
			String message) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}

	/**
	 * Check internet connection
	 * 
	 * @return true if have connect to internet, false if haven't
	 */
	public static final boolean checkInternetConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifiNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			return true;
		}

		NetworkInfo mobileNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected()) {
			return true;
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		}

		return false;
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null)
			return;

		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
				MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		View view = null;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, listView);
			if (i == 0)
				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
						LayoutParams.WRAP_CONTENT));

			view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	public static void setGridViewHeightBasedOnChildren(GridView gridView,
			int columns, int horizontalSpacing) {
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int items = listAdapter.getCount();
		if (items == 0)
			return;
		int totalHeight = 0;
		int rows = 0;

		View listItem = listAdapter.getView(0, null, gridView);
		listItem.measure(0, 0);
		totalHeight = listItem.getMeasuredHeight() + horizontalSpacing;

		float x = 1;
		if (items > columns) {
			x = items / columns;
			int r = items % columns;
			if (r != 0)
				rows = (int) (x + 1);
			else
				rows = (int) x;
			totalHeight *= rows;
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight;
		gridView.setLayoutParams(params);
		gridView.requestLayout();
	}

	public static Boolean is_changed_myLocale = false;

	// thay doi ngon ngu cho app
	public static void changeLang(Activity _activity, String lang) {
		if (lang.equalsIgnoreCase(""))
			return;
		Locale myLocale = new Locale(lang);
		Locale.setDefault(myLocale);
		android.content.res.Configuration config = new android.content.res.Configuration();
		config.locale = myLocale;
		_activity.getResources().updateConfiguration(config,
				_activity.getResources().getDisplayMetrics());
		ConfigApp.KEY_LOCATION = lang;
		is_changed_myLocale = true;
		_activity.onConfigurationChanged(config);
		is_changed_myLocale = false;
	}

	private static final String KEY_LANGUAGE = "KEY_LANGUAGE";
	private static final String KEY_LOCATION = "KEY_LOCATION";
	private static final String KEY_NOTIFICATION = "KEY NOTIFICATION";

	public static void loadShared(SharedPreferences sharedPrefs) {
		ConfigApp.KEY_LANGUAGE = sharedPrefs.getString(KEY_LANGUAGE, "");
		ConfigApp.KEY_LOCATION = sharedPrefs.getString("KEY_LOCATION", "");
		ConfigApp.KEY_NOTIFICATION = sharedPrefs.getInt(KEY_NOTIFICATION, 1);
	}

	public static void saveShared(SharedPreferences sharedPrefs) {
		SharedPreferences.Editor editor = sharedPrefs.edit();

		editor.putString(KEY_LANGUAGE, ConfigApp.KEY_LANGUAGE);
		editor.putString(KEY_LOCATION, ConfigApp.KEY_LOCATION);
		editor.putInt(KEY_NOTIFICATION, ConfigApp.KEY_NOTIFICATION);

		editor.commit();
	}

}
