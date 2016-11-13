package com.intocable.object;

public class Photos {

	private String id;
	private String url;
	private String title;

	public Photos(String id, String url, String title) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
	}

	public Photos() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (!(o instanceof Photos))
			return false;

		Photos photo = (Photos) o;
		return (photo.getId().equals(id) && photo.getUrl().equals(url));
	}
}
