package com.intocable.object;

public class Events extends TimeObject {

	private String ID;
	private String Time;
	private String Day;
	private String Title;
	private String City;
	private String State;
	private String Zip;

	public Events() {
		super();
	}

	public Events(String iD, String time, String day, String title,
			String city, String state, String zip) {
		super();
		ID = iD;
		Time = time;
		Day = day;
		Title = title;
		City = city;
		State = state;
		Zip = zip;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public String getDay() {
		return Day;
	}

	public void setDay(String day) {
		Day = day;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getZip() {
		return Zip;
	}

	public void setZip(String zip) {
		Zip = zip;
	}

}
