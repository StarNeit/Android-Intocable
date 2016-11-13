package com.intocable.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import android.content.Context;
import android.util.Log;

public class GetStreamingUrl {

private static String LOGTAG = "GetStreamingUrl";
private Context mContext;

public GetStreamingUrl(Context context) {
    Log.i(LOGTAG, "call to constructor");
    this.mContext = context;

}

public LinkedList<String> getStreamingUrl(String url) {

    Log.i(LOGTAG, "get streaming url");
    final BufferedReader br;
    String murl = null;
    LinkedList<String> murls = null;
    try {
        URLConnection mUrl = new URL(url).openConnection();
        br = new BufferedReader(
                new InputStreamReader(mUrl.getInputStream()));
        murls = new LinkedList<String>();
        while (true) {
            try {
                String line = br.readLine();

                if (line == null) {
                    break;
                }
                murl = parseLine(line);
                if (murl != null && !murl.equals("")) {
                    murls.add(murl);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    Log.i(LOGTAG, "url to stream :" + murl);
    return murls;
}

private String parseLine(String line) {
    if (line == null) {
        return null;
    }
    String trimmed = line.trim();
    if (trimmed.indexOf("http") >= 0) {
        return trimmed.substring(trimmed.indexOf("http"));
    }
    return "";
        }
  } 