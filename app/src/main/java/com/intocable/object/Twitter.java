package com.intocable.object;

public class Twitter extends TimeObject {

	private String ID;
	private String Description;
	private String LargeImage;
	private String Date;
	private String Title;
	private String Body;

	public Twitter() {
		super();
	}

	public Twitter(java.util.Date date, String iD, String description,
			String largeImage, String date2, String title, String body) {
		super(date);
		ID = iD;
		Description = description;
		LargeImage = largeImage;
		Date = date2;
		Title = title;
		Body = body;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getLargeImage() {
		return LargeImage;
	}

	public void setLargeImage(String largeImage) {
		LargeImage = largeImage;
	}

	public String getdate() {
		return Date;
	}

	public void setPosted(String date) {
		Date = date;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getBody() {
		return Body;
	}

	public void setBody(String body) {
		Body = body;
	}

}
