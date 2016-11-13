package com.intocable.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.w3c.dom.Document;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {

			byte[] bytes = new byte[buffer_size];
			for (;;) {
				// Read byte from input stream

				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;

				// Write byte from output stream
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
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

	public static String toString(Document doc) {
		try {
			StringWriter sw = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer
					.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			return sw.toString();
		} catch (Exception ex) {
			throw new RuntimeException("Error converting to String", ex);
		}
	}

	public static ArrayList<Video> getStreamingUrisFromYouTubePage(String ytUrl)
			throws IOException {

		final String TAG = "DOWNLOAD YOUTUBE";
		if (ytUrl == null) {
			return null;
		}

		// Remove any query params in query string after the watch?v=<vid> in
		// e.g.
		// http://www.youtube.com/watch?v=0RUPACpf8Vs&feature=youtube_gdata_player
		int andIdx = ytUrl.indexOf('&');
		if (andIdx >= 0) {
			ytUrl = ytUrl.substring(0, andIdx);
		}

		// Get the HTML response
		String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:8.0.1)";
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
				userAgent);
		HttpGet request = new HttpGet(ytUrl);
		HttpResponse response = client.execute(request);
		String html = "";
		InputStream in = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder str = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			str.append(line.replace("\\u0026", "&"));
		}
		in.close();
		html = str.toString();

		// Parse the HTML response and extract the streaming URIs
		if (html.contains("verify-age-thumb")) {
			return null;
		}

		if (html.contains("das_captcha")) {
			return null;
		}

		Pattern p = Pattern.compile("stream_map\": \"(.*?)?\"");
		// Pattern p = Pattern.compile("/stream_map=(.[^&]*?)\"/");
		Matcher m = p.matcher(html);
		List<String> matches = new ArrayList<String>();
		while (m.find()) {
			matches.add(m.group());
		}

		if (matches.size() != 1) {
			return null;
		}

		String urls[] = matches.get(0).split(",");
		HashMap<String, String> foundArray = new HashMap<String, String>();
		for (String ppUrl : urls) {
			String url = URLDecoder.decode(ppUrl, "UTF-8");

			Pattern p1 = Pattern.compile("itag=([0-9]+?)[&]");
			Matcher m1 = p1.matcher(url);
			String itag = null;
			if (m1.find()) {
				itag = m1.group(1);
			}

			Pattern p2 = Pattern.compile("sig=(.*?)[&]");
			Matcher m2 = p2.matcher(url);
			String sig = null;
			if (m2.find()) {
				sig = m2.group(1);
			}

			Pattern p3 = Pattern.compile("url=(.*?)[&]");
			Matcher m3 = p3.matcher(ppUrl);
			String um = null;
			if (m3.find()) {
				um = m3.group(1);
			}

			if (itag != null && sig != null && um != null) {
				foundArray.put(itag, URLDecoder.decode(um, "UTF-8") + "&"
						+ "signature=" + sig);
			}
		}

		if (foundArray.size() == 0) {
			return null;
		}

		HashMap<String, Meta> typeMap = new HashMap<String, Meta>();
		typeMap.put("13", new Meta("13", "3GP", "Low Quality - 176x144"));
		typeMap.put("17", new Meta("17", "3GP", "Medium Quality - 176x144"));
		typeMap.put("36", new Meta("36", "3GP", "High Quality - 320x240"));
		typeMap.put("5", new Meta("5", "FLV", "Low Quality - 400x226"));
		typeMap.put("6", new Meta("6", "FLV", "Medium Quality - 640x360"));
		typeMap.put("34", new Meta("34", "FLV", "Medium Quality - 640x360"));
		typeMap.put("35", new Meta("35", "FLV", "High Quality - 854x480"));
		typeMap.put("43", new Meta("43", "WEBM", "Low Quality - 640x360"));
		typeMap.put("44", new Meta("44", "WEBM", "Medium Quality - 854x480"));
		typeMap.put("45", new Meta("45", "WEBM", "High Quality - 1280x720"));
		typeMap.put("18", new Meta("18", "MP4", "Medium Quality - 480x360"));
		typeMap.put("22", new Meta("22", "MP4", "High Quality - 1280x720"));
		typeMap.put("37", new Meta("37", "MP4", "High Quality - 1920x1080"));
		typeMap.put("33", new Meta("38", "MP4", "High Quality - 4096x230"));

		ArrayList<Video> videos = new ArrayList<Video>();

		for (String format : typeMap.keySet()) {
			Meta meta = typeMap.get(format);

			if (foundArray.containsKey(format)) {
				Video newVideo = new Video(meta.ext, meta.type,
						foundArray.get(format));
				videos.add(newVideo);
			}
		}

		return videos;
	}
}

class Meta {
	public String num;
	public String type;
	public String ext;

	Meta(String num, String ext, String type) {
		this.num = num;
		this.ext = ext;
		this.type = type;
	}
}

class Video {
	public String ext = "";
	public String type = "";
	public String url = "";

	Video(String ext, String type, String url) {
		this.ext = ext;
		this.type = type;
		this.url = url;
	}

}