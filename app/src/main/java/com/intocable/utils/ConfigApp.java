package com.intocable.utils;

import java.util.ArrayList;

/**
 * @author HuongTa
 * 
 */
public class ConfigApp {

	private static final String WS_ROOT = "http://www.grupointocable.com/_ws/intocable/";
	public static final String SHARED_PREF = "sharedPref";
	public static String KEY_LANGUAGE = ""; //gia tri: "english/" hoac "spanish/"
	public static String KEY_LOCATION = "";
	public static int KEY_NOTIFICATION = 1;
	

	public static final String link_ws(String type) {
		String link = WS_ROOT + ConfigApp.KEY_LANGUAGE + "xml/" + type
				+ "/index.xml";
		return link;
	}

	public static final ArrayList<String> imageUrl = new ArrayList<String>();

}
