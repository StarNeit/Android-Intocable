package com.intocable.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import android.util.Pair;

public class XMLParserHelper {

	private XmlPullParser parser;
	private String xml = "";
	private String namespace = null;

	public static String getStringFromUrl(String url,
			List<NameValuePair> headers, List<NameValuePair> params)
			throws UnsupportedEncodingException {
		String result = "";

		// create http client
		DefaultHttpClient client = new DefaultHttpClient();

		// create http post
		HttpPost postRequest = new HttpPost(url);

		// set post data
		if (params != null && params.size() != 0) {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					HTTP.UTF_8);
			postRequest.setEntity(entity);
		}

		// set post header
		if (headers != null && !headers.isEmpty()) {
			Header[] hs = postRequest.getAllHeaders();
			for (Header h : hs) {
				postRequest.removeHeader(h);
			}

			for (NameValuePair h : headers) {
				postRequest
						.setHeader(new BasicHeader(h.getName(), h.getValue()));
			}
		}

		// create http response
		try {
			// post request
			HttpResponse response = client.execute(postRequest);

			// get entity return
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream in = entity.getContent();

				// log result
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				StringBuilder builder = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null)
					builder.append(line);
				result = builder.toString();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			result = "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result = "";
		}

		return result;
	}

	public XMLParserHelper(String xml, String namespace)
			throws XmlPullParserException {
		super();
		this.xml = xml;
		this.namespace = namespace;

		// create factory
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);

		// create parser
		parser = factory.newPullParser();
		if (parser != null)
			parser.setInput(new StringReader(xml));
	}

	public XMLParserHelper(String url, List<NameValuePair> headers,
			List<NameValuePair> params, String namespace)
			throws UnsupportedEncodingException, XmlPullParserException {
		super();

		this.xml = getStringFromUrl(url, headers, params);
		this.namespace = namespace;

		// create factory
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);

		// create parser
		parser = factory.newPullParser();
		if (parser != null && !xml.isEmpty())
			parser.setInput(new StringReader(xml));

	}

	public void next() {
		try {
			if (parser != null
					&& parser.getEventType() != XmlPullParser.END_DOCUMENT)
				parser.next();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void nextToken() {
		try {
			if (parser != null
					&& parser.getEventType() != XmlPullParser.END_DOCUMENT)
				parser.nextToken();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void nextTag() {
		try {
			if (parser != null
					&& parser.getEventType() != XmlPullParser.END_DOCUMENT)
				parser.nextTag();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isEndDocument() {
		try {
			if (parser != null)
				return (parser.getEventType() == XmlPullParser.END_DOCUMENT);

		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public String getValueOfTag(String tagName) throws XmlPullParserException,
			IOException {
		String value = "";
		if (parser != null) {
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType != XmlPullParser.START_TAG) {
					eventType = parser.nextToken();
					continue;
				}

				if (parser.getName().equals(tagName)) {
					parser.require(XmlPullParser.START_TAG, namespace, tagName);
					value = parser.nextText();
					parser.require(XmlPullParser.END_TAG, namespace, tagName);
					break;
				} else
					eventType = parser.nextToken();
			}
		}
		return value;
	}

	public ArrayList<Pair<String, String>> getAttributesOfTag(String tagName)
			throws XmlPullParserException, IOException {
		if (parser != null) {
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType != XmlPullParser.START_TAG) {
					eventType = parser.nextToken();
					continue;
				}

				if (parser.getName().equals(tagName)) {
					ArrayList<Pair<String, String>> attributes = new ArrayList<Pair<String, String>>();
					if (parser.getEventType() != XmlPullParser.START_TAG
							|| !parser.getName().equals(tagName))
						parser.require(XmlPullParser.START_TAG, namespace,
								tagName);
					int count = parser.getAttributeCount();
					for (int i = 0; i < count; i++) {
						String key = parser.getAttributeName(i);
						String value = parser.getAttributeType(i);
						Pair<String, String> attribute = new Pair<String, String>(
								key, value);
						attributes.add(attribute);
					}
					return attributes;
				} else
					eventType = parser.nextToken();
			}
		}
		return null;
	}

	public String getAttributeOfTag(String tagName, String attributeName)
			throws XmlPullParserException, IOException {
		if (parser != null) {
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType != XmlPullParser.START_TAG) {
					eventType = parser.nextToken();
					continue;
				}

				if (parser.getName().equals(tagName)) {
					if (parser.getEventType() != XmlPullParser.START_TAG
							|| !parser.getName().equals(tagName))
						parser.require(XmlPullParser.START_TAG, namespace,
								tagName);
					String attribute = parser.getAttributeValue(namespace,
							attributeName);
					return attribute;
				} else
					eventType = parser.nextToken();
			}
		}
		return "";
	}

	public String getCDATAOfTag(String tagName) throws XmlPullParserException,
			IOException {
		if (parser != null) {
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType != XmlPullParser.START_TAG) {
					eventType = parser.nextToken();
					continue;
				}

				if (parser.getName().equals(tagName)) {
					StringBuilder builder = new StringBuilder();
					parser.require(XmlPullParser.START_TAG, namespace, tagName);
					int entity = parser.nextToken();
					while (entity != XmlPullParser.END_TAG) {
						if (entity == XmlPullParser.CDSECT) {
							builder.append(parser.getText());
						}
						entity = parser.nextToken();
					}
					return builder.toString();
				} else
					eventType = parser.nextToken();
			}
		}
		return "";
	}
}
