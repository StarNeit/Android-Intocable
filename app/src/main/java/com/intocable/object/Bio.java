package com.intocable.object;

public class Bio extends BaseObject {

	private String urlImage = "";
	private String bio = "";

	public Bio() {
		super();
	}

	public Bio(String url, String urlImage, String bio) {
		super(url);
		this.urlImage = urlImage;
		this.bio = bio;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

}
