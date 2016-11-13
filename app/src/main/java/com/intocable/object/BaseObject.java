package com.intocable.object;

public class BaseObject {

	private String url = "";

	public BaseObject() {
		super();
	}

	public BaseObject(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
