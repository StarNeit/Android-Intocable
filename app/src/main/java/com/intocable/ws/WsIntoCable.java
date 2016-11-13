package com.intocable.ws;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.intocable.utils.ConfigApp;
import com.intocable.utils.XMLParserHelper;

public class WsIntoCable {

	private static final String WS_ROOT = "http://www.grupointocable.com/_ws/intocable/";
	public static final String TYPE_BIO = "bio";
	public static final String TYPE_MUSIC = "music";
	// private static final String COMMENT_TYPE = "comments";
	public static final String TYPE_MERCHANDISE = "products";
	public static final String TYPE_PHOTO = "photos";
	public static final String TYPE_VIDEO = "videos";
	public static final String TYPE_ABOUT = "about";
	public static final String TYPE_COMMENT = "eventdetails";
	
	public static final String getLink(String type) {
		String link = WS_ROOT + ConfigApp.KEY_LANGUAGE + "xml/" + type
				+ "/index.xml";
		return link;
	}

	public static final String getXML(String url) {
		String xml = "";
		try {
			xml = XMLParserHelper.getStringFromUrl(url, null, null);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xml = "";
		}
		return xml;
	}

	public static final String postComment(String name, String comment,
			String eventId) {
		// String url = ConfigApp.link_ws(COMMENT_TYPE);
		String url = "http://grupointocable.com/_ws/intocable/english/comments/index.php";
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("key",
				"5583ba04d14de2e2a04c2ff7dcd7f504"));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("comment", comment));
		params.add(new BasicNameValuePair("thread", eventId));
		String xml = "";
		try {
			xml = XMLParserHelper.getStringFromUrl(url, null, params);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xml = "";
		}
		return xml;
	}

	public static final JSONObject getTwiter(String url) {

		return RestfulWSHelper.doGet(url);
	}

	public static final JSONObject getfacebook(String url) {

		return RestfulWSHelper.getJSONFromUrl(url);
	}
}
