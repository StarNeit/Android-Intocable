package com.intocable.object;

import java.util.Date;

public class News extends TimeObject {

	private String ID;
	private String SmallImage;
	private String LargeImage;
	private String Posted;
	private String Title;
	private String Body;
	private Date datePosted;

	public News() {
		super();
	}

	public News(Date date, String iD, String smallImage, String largeImage,
			String posted, String title, String body, Date datePosted) {
		super(date);
		ID = iD;
		SmallImage = smallImage;
		LargeImage = largeImage;
		Posted = posted;
		Title = title;
		Body = body;
		this.datePosted = datePosted;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getSmallImage() {
		return SmallImage;
	}

	public void setSmallImage(String smallImage) {
		SmallImage = smallImage;
	}

	public String getLargeImage() {
		return LargeImage;
	}

	public void setLargeImage(String largeImage) {
		LargeImage = largeImage;
	}

	public String getPosted() {
		return Posted;
	}

	public void setPosted(String posted) {
		Posted = posted;
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

	public Date getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

}
