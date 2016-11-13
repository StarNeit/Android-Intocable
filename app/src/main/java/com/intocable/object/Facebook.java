package com.intocable.object;

import java.util.Date;

public class Facebook extends TimeObject {

	private String Title;
	private String Image = "";
	private String Time;
	private String content;

	public Facebook() {
		super();
	}

	public Facebook(Date date, String title, String image, String time,
			String content) {
		super(date);
		Title = title;
		Image = image;
		Time = time;
		this.content = content;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
