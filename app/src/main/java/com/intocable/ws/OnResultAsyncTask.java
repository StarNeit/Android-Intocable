package com.intocable.ws;

import org.json.JSONObject;

/**
 * Nhan ket qua tra ve tu Asynctask
 * 
 * @author NgVietAn
 * 
 */
public interface OnResultAsyncTask {

	public void showResult(JSONObject result);

	public void showResult(String xml);
}
