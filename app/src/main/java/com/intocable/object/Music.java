package com.intocable.object;

public class Music extends BaseObject {

	private String ID;
	private String CanBuy;
	private String CanPlay;
	private String Buy;
	private String Play;
	private String Artist;
	private String Title;

	public Music() {
		super();
	}

	public Music(String url, String iD, String canBuy, String canPlay,
			String buy, String play, String artist, String title) {
		super(url);
		ID = iD;
		CanBuy = canBuy;
		CanPlay = canPlay;
		Buy = buy;
		Play = play;
		Artist = artist;
		Title = title;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCanBuy() {
		return CanBuy;
	}

	public void setCanBuy(String canBuy) {
		CanBuy = canBuy;
	}

	public String getCanPlay() {
		return CanPlay;
	}

	public void setCanPlay(String canPlay) {
		CanPlay = canPlay;
	}

	public String getBuy() {
		return Buy;
	}

	public void setBuy(String buy) {
		Buy = buy;
	}

	public String getPlay() {
		return Play;
	}

	public void setPlay(String play) {
		Play = play;
	}

	public String getArtist() {
		return Artist;
	}

	public void setArtist(String artist) {
		Artist = artist;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (!(o instanceof Music))
			return false;

		Music music = (Music) o;
		return music.getID().equals(ID);
	}
}
