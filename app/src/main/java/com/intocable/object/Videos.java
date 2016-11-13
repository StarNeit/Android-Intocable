package com.intocable.object;

public class Videos {

	private String ID;
	private String Name;
	private String PlayURL;
	private String BackgroundURL;

	public Videos() {
		super();
	}

	public Videos(String iD, String name, String playURL, String backgroundURL) {
		super();
		ID = iD;
		Name = name;
		PlayURL = playURL;
		BackgroundURL = backgroundURL;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPlayURL() {
		return PlayURL;
	}

	public void setPlayURL(String playURL) {
		PlayURL = playURL;
	}

	public String getBackgroundURL() {
		return BackgroundURL;
	}

	public void setBackgroundURL(String backgroundURL) {
		BackgroundURL = backgroundURL;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (!(o instanceof Videos))
			return false;

		Videos video = (Videos) o;
		return (video.getID().equals(ID) && video.getPlayURL().equals(PlayURL));
	}
}
