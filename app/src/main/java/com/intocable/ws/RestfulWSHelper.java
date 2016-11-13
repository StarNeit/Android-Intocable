package com.intocable.ws;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ErrorManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;

import android.webkit.MimeTypeMap;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

/**
 * Provide helper to call Restful Web service.
 * 
 */
public class RestfulWSHelper {
	static JSONObject obj = null;

	private static final boolean SSL_MODE = true;

	private static String getResponseText(InputStream inStream) {
		return new Scanner(inStream).useDelimiter("\\A").next();
	}
	
	public static JSONObject getJSONFromUrl(String url) {

        // Making HTTP request
        InputStream is = null;
        JSONObject jobj = null;
        String json = null;
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();


            StringBuilder buffer = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, HTTP.UTF_8));

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                json = buffer.toString();

            } finally {
                is.close();
                reader.close();
            }

            try {
                jobj = new JSONObject(json);
            } catch (JSONException e) {
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // try parse the string to a JSON object

        // return JSON String
        return jobj;

    }
	/**
	 * Make a GET request.
	 * 
	 * @param url
	 *            Request link + param.
	 * @return Response content in JSON.
	 */
	public static JSONObject doGet(String url) {
		boolean event = true;
		// ErrorObject error = null;

		JSONObject json = null;

		HttpClient httpclient = RestfulWSHelper.getHttpClient();
		HttpGet request = new HttpGet(url);

		HttpResponse response;
		try {
			response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				json = new JSONObject(getResponseText(instream));
				instream.close();
			}
		} catch (ClientProtocolException e) {
			event = false;
			 e.printStackTrace();
		} catch (IOException e) {
			event = false;
			 e.printStackTrace();
		} catch (JSONException e) {
			event = false;
			 e.printStackTrace();
		}
		//
		// if (!event) {
		// JSONObject errorObject = new JSONObject();
		// try {
		// // errorObject.put("event", false);
		// // errorObject.put("error", error.getErrorId());
		// // errorObject.put("message", error.getErrorMessage());
		// } catch (JSONException e) {
		// e.printStackTrace();
		// return null;
		// }
		//
		// return errorObject;
		// }

		return json;
	}

	/**
	 * Make a POST request.
	 * 
	 * @param url
	 *            Request link.
	 * @param nameValuePairs
	 *            From data.
	 * @return Response content in JSON
	 */
	public static JSONObject doPost(String url,
			List<NameValuePair> nameValuePairs) {

		// if (GlobalConfig.VS_DEBUG)
		// Log.d(GlobalConfig.APP_ID, "Request: " + nameValuePairs.toString());

		boolean event = true;
		// ErrorObject error = null;

		JSONObject json = null;

		HttpClient httpclient = RestfulWSHelper.getHttpClient();
		HttpPost request = new HttpPost(url);

		HttpResponse response;
		try {
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					nameValuePairs);

			formEntity.setContentEncoding("UTF-8");

			request.setEntity(formEntity);

			response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				json = new JSONObject(getResponseText(instream));
				instream.close();
			}
		} catch (ClientProtocolException e) {
			event = false;
			// error = ErrorManager.CLIENT_PROTOCOL_EXCEPTION_ERROR;
		} catch (IOException e) {
			event = false;
			// error = ErrorManager.IO_EXCEPTION_ERROR;
		} catch (JSONException e) {
			event = false;
			// error = ErrorManager.JSON_EXCEPTION_ERROR;
		}

		if (!event) {
			JSONObject errorObject = new JSONObject();
			try {
				errorObject.put("event", false);
				// errorObject.put("error", error.getErrorId());
				// errorObject.put("message", error.getErrorMessage());
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}

			return errorObject;
		}

		return json;
	}

	public static JSONObject doPost(String url,
			List<NameValuePair> nameValuePairs, List<NameValuePair> headers) {

		// if (GlobalConfig.VS_DEBUG)
		// Log.d(GlobalConfig.APP_ID, "Request: " + nameValuePairs.toString());

		boolean event = true;
		// ErrorObject error = null;

		JSONObject json = null;

		HttpClient httpclient = RestfulWSHelper.getHttpClient();
		HttpPost request = new HttpPost(url);

		HttpResponse response;
		try {
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					nameValuePairs);

			formEntity.setContentEncoding("UTF-8");

			request.setEntity(formEntity);
			for (NameValuePair header : headers) {
				request.setHeader(header.getName(), header.getValue());
			}
			response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				json = new JSONObject(getResponseText(instream));
				instream.close();
			}
		} catch (ClientProtocolException e) {
			event = false;
			// error = ErrorManager.CLIENT_PROTOCOL_EXCEPTION_ERROR;
		} catch (IOException e) {
			event = false;
			// error = ErrorManager.IO_EXCEPTION_ERROR;
		} catch (JSONException e) {
			event = false;
			// error = ErrorManager.JSON_EXCEPTION_ERROR;
		}

		if (!event) {
			JSONObject errorObject = new JSONObject();
			try {
				errorObject.put("event", false);
				// errorObject.put("error", error.getErrorId());
				// errorObject.put("message", error.getErrorMessage());
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}

			return errorObject;
		}

		return json;
	}

	public static JSONObject doGet1(String url,
			List<NameValuePair> nameValuePairs) {

		// if (GlobalConfig.VS_DEBUG)
		// Log.d(GlobalConfig.APP_ID, "Request: " + nameValuePairs.toString());

		boolean event = true;
		// ErrorObject error = null;

		JSONObject json = null;

		HttpClient httpclient = RestfulWSHelper.getHttpClient();
		HttpGet request = new HttpGet(url);

		HttpResponse response;
		try {
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					nameValuePairs);

			formEntity.setContentEncoding("UTF-8");

			// request.setEntity(formEntity);

			response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				json = new JSONObject(getResponseText(instream));
				instream.close();
			}
		} catch (ClientProtocolException e) {
			event = false;
			// error = ErrorManager.CLIENT_PROTOCOL_EXCEPTION_ERROR;
		} catch (IOException e) {
			event = false;
			// error = ErrorManager.IO_EXCEPTION_ERROR;
		} catch (JSONException e) {
			event = false;
			// error = ErrorManager.JSON_EXCEPTION_ERROR;
		}

		if (!event) {
			JSONObject errorObject = new JSONObject();
			try {
				errorObject.put("event", false);
				// errorObject.put("error", error.getErrorId());
				// errorObject.put("message", error.getErrorMessage());
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}

			return errorObject;
		}

		return json;
	}

	/**
	 * Make a POST request with raw data.
	 * 
	 * @author Phu Dinh
	 * 
	 * @param url
	 *            Request link.
	 * @param data
	 *            From data.
	 * @return Response content in JSON
	 */
	public static JSONObject doRawPost(String url, String data) {

		// if (GlobalConfig.VS_DEBUG)
		// Log.d(GlobalConfig.APP_ID, "Request: " + data);

		boolean event = true;
		// ErrorObject error = null;

		JSONObject json = null;

		HttpClient httpclient = RestfulWSHelper.getHttpClient();
		HttpPost request = new HttpPost(url);

		HttpResponse response;
		try {

			StringEntity s = new StringEntity(data, "UTF-8");
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");

			request.setEntity(s);
			request.setEntity(s);

			response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				json = new JSONObject(getResponseText(instream));
				instream.close();
			}
		} catch (ClientProtocolException e) {
			event = false;
			// error = ErrorManager.CLIENT_PROTOCOL_EXCEPTION_ERROR;
		} catch (IOException e) {
			event = false;
			// error = ErrorManager.IO_EXCEPTION_ERROR;
		} catch (JSONException e) {
			event = false;
			// error = ErrorManager.JSON_EXCEPTION_ERROR;
		}

		if (!event) {
			JSONObject errorObject = new JSONObject();
			try {
				errorObject.put("event", false);
				// errorObject.put("error", error.getErrorId());
				// errorObject.put("message", error.getErrorMessage());
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}

			return errorObject;
		}

		return json;
	}

	/**
	 * Make a PUT request.
	 * 
	 * @param url
	 *            Request link.
	 * @param c
	 *            Body content.
	 * @param authentication_key
	 *            Authentication key.
	 * @return Response content in JSON
	 */
	public static JSONObject doPut(String url,
			List<NameValuePair> nameValuePairs) {
		boolean event = true;
		// ErrorObject error = null;

		JSONObject json = null;

		HttpClient httpclient = RestfulWSHelper.getHttpClient();
		HttpPut request = new HttpPut(url);

		HttpResponse response;
		try {
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					nameValuePairs);

			formEntity.setContentEncoding("UTF-8");

			request.setEntity(formEntity);

			response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				json = new JSONObject(getResponseText(instream));
				instream.close();
			}
		} catch (ClientProtocolException e) {
			event = false;
			// error = ErrorManager.CLIENT_PROTOCOL_EXCEPTION_ERROR;
		} catch (IOException e) {
			event = false;
			// error = ErrorManager.IO_EXCEPTION_ERROR;
		} catch (JSONException e) {
			event = false;
			// error = ErrorManager.JSON_EXCEPTION_ERROR;
		}

		if (!event) {
			JSONObject errorObject = new JSONObject();
			try {
				errorObject.put("event", false);
				// errorObject.put("error", error.getErrorId());
				// errorObject.put("message", error.getErrorMessage());
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}

			return errorObject;
		}

		return json;
	}

	/**
	 * Make a DELETE request.
	 * 
	 * @param url
	 *            Request link + param.
	 * @param authentication_key
	 *            Authentication key.
	 * @return Response content in JSON
	 */
	public static JSONObject doDelete(String url) {
		boolean event = true;
		// ErrorObject error = null;

		JSONObject json = null;

		HttpClient httpclient = RestfulWSHelper.getHttpClient();
		HttpDelete request = new HttpDelete(url);

		HttpResponse response;
		try {
			response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				json = new JSONObject(getResponseText(instream));
				instream.close();
			}
		} catch (ClientProtocolException e) {
			event = false;
			// error = ErrorManager.CLIENT_PROTOCOL_EXCEPTION_ERROR;
		} catch (IOException e) {
			event = false;
			// error = ErrorManager.IO_EXCEPTION_ERROR;
		} catch (JSONException e) {
			event = false;
			// error = ErrorManager.JSON_EXCEPTION_ERROR;
		}

		if (!event) {
			JSONObject errorObject = new JSONObject();
			// try {
			// errorObject.put("event", false);
			// // errorObject.put("error", error.getErrorId());
			// // errorObject.put("message", error.getErrorMessage());
			// } catch (JSONException e) {
			// e.printStackTrace();
			// return null;
			// }

			// return errorObject;
			// }
		}
		return json;
	}

	private static DefaultHttpClient getHttpClient() {
		if (!SSL_MODE)
			return new DefaultHttpClient();

		KeyStore trustStore;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (KeyStoreException e) {
			e.printStackTrace();
			return null;
		} catch (KeyManagementException e) {
			e.printStackTrace();
			return null;
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (CertificateException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static class MySSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {

				@Override
				public void checkClientTrusted(X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

	public static JSONObject WSRestclient(String url,
			ArrayList<NameValuePair> params)
			throws UnsupportedEncodingException {
		ArrayList<NameValuePair> headers = new ArrayList<NameValuePair>();
		headers.add(new BasicNameValuePair("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64)"));
		headers.add(new BasicNameValuePair("Content-Type",
				"application/x-www-form-urlencoded"));
		return WSRestclient(url, headers, params);
	}

	public static JSONObject WSRestclient(String url,
			ArrayList<NameValuePair> headers, ArrayList<NameValuePair> params)
			throws UnsupportedEncodingException {
		HttpPost request = new HttpPost(url);

		Header[] hs = request.getAllHeaders();
		for (Header h : hs) {
			request.removeHeader(h);
		}

		for (NameValuePair h : headers) {
			request.setHeader(new BasicHeader(h.getName(), h.getValue()));
			// request.addHeader(h.getName(), h.getValue());
		}

		if (!params.isEmpty()) {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					HTTP.UTF_8);
			// entity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
			// entity.setContentEncoding("UTF-8");
			request.setEntity(entity);
		}
		return executeRequest(request, url);
	}

	public static JSONObject WSRestclient(String url,
			ArrayList<NameValuePair> headers, JSONObject params)
			throws UnsupportedEncodingException {
		HttpPost request = new HttpPost(url);

		for (NameValuePair h : headers) {
			request.setHeader(h.getName(), h.getValue());
		}

		if (params != null) {
			StringEntity se = new StringEntity(params.toString());
			se.setContentType("application/json; charset=UTF-8");
			se.setContentEncoding("UTF-8");
			request.setEntity(se);
		}
		return executeRequest(request, url);
	}

	
	private static JSONObject executeRequest(HttpUriRequest request, String url) {
		JSONObject json = null;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse httpResponse;
		Header[] head = request.getAllHeaders();

		try {
			httpResponse = client.execute(request);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader r = new BufferedReader(new InputStreamReader(
						instream));
				StringBuilder total = new StringBuilder();
				String line;
				while ((line = r.readLine()) != null) {
					total.append(line);
				}
				json = new JSONObject(total.toString());
				instream.close();
				instream.close();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
		return json;
	}

	public static JSONObject excuteAjaxRequest(Context context, String url,
			ArrayList<NameValuePair> data) {
		obj = null;
		AQuery aq = new AQuery(context);
		Map<String, String> params = new HashMap<String, String>();
		for (NameValuePair d : data) {
			params.put(d.getName(), d.getValue());
		}

		aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject object,
					AjaxStatus status) {
				// TODO Auto-generated method stub
				super.callback(url, object, status);
				obj = object;

			}
		});
		while (null == obj)
			;
		return obj;
	}

	/**
	 * Upload only file
	 * 
	 * @param url
	 * @param path_file
	 * @return
	 */
	public static JSONObject upLoadFile(String url, String path_file) {
		JSONObject json = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		File file = new File(path_file);
		Uri selectedUri = Uri.fromFile(file);
		String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri
				.toString());
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				fileExtension);

		ContentBody fb = new FileBody(file, mimeType);
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.STRICT);
		entity.addPart("image", fb);
		httppost.setEntity(entity);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream instream;
		try {
			HttpEntity httpEntity = response.getEntity();
			if (entity != null) {

				instream = httpEntity.getContent();
				// Log.i("Hoa debug","instream = "+getResponseText(instream));
				json = new JSONObject(getResponseText(instream));
				instream.close();
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;

	}

	/**
	 * Upload file and some variables
	 * 
	 * @param url
	 * @param path_file
	 * @param nameValuePairs
	 * @return
	 */
	public static JSONObject upLoadFile(String url, String path_file,
			List<NameValuePair> nameValuePairs) {
		JSONObject json = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		File file = new File(path_file);
		Uri selectedUri = Uri.fromFile(file);
		String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri
				.toString());
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				fileExtension);

		ContentBody fb = new FileBody(file, mimeType);
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.STRICT);
		entity.addPart("image", fb);
		UrlEncodedFormEntity formEntity = null;
		try {
			if (nameValuePairs != null && nameValuePairs.size() != 0) {
				for (NameValuePair values : nameValuePairs) {
					entity.addPart(values.getName(), new StringBody(values
							.getValue().toString()));

				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		httppost.setEntity(entity);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream instream;
		try {
			HttpEntity httpEntity = response.getEntity();
			if (entity != null) {

				instream = httpEntity.getContent();
				// Log.i("Hoa debug","instream = "+getResponseText(instream));
				json = new JSONObject(getResponseText(instream));
				instream.close();
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json;

	}
	
	public static JSONArray doGetArray(String url) {
		boolean event = true;
		// ErrorObject error = null;

		JSONArray json = null;

		HttpClient httpclient = RestfulWSHelper.getHttpClient();
		HttpGet request = new HttpGet(url);

		HttpResponse response;
		try {
			response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				json = new JSONArray(getResponseText(instream));
				instream.close();
			}
		} catch (ClientProtocolException e) {
			event = false;
			// error = ErrorManager.CLIENT_PROTOCOL_EXCEPTION_ERROR;
		} catch (IOException e) {
			event = false;
			// error = ErrorManager.IO_EXCEPTION_ERROR;
		} catch (JSONException e) {
			event = false;
			// error = ErrorManager.JSON_EXCEPTION_ERROR;
		}
		//
		// if (!event) {
		// JSONObject errorObject = new JSONObject();
		// try {
		// // errorObject.put("event", false);
		// // errorObject.put("error", error.getErrorId());
		// // errorObject.put("message", error.getErrorMessage());
		// } catch (JSONException e) {
		// e.printStackTrace();
		// return null;
		// }
		//
		// return errorObject;
		// }

		return json;
	}
}
