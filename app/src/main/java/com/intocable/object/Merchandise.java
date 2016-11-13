package com.intocable.object;

public class Merchandise extends BaseObject {

	private String Title;
	private String ID;
	private String SmallImageURL;

	public Merchandise() {
		super();
	}

	public Merchandise(String url, String title, String iD, String smallImageURL) {
		super(url);
		Title = title;
		ID = iD;
		SmallImageURL = smallImageURL;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getSmallImageURL() {
		return SmallImageURL;
	}

	public void setSmallImageURL(String smallImageURL) {
		SmallImageURL = smallImageURL;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (!(o instanceof Merchandise))
			return false;

		Merchandise merchandise = (Merchandise) o;
		return (merchandise.getID().equals(ID) && merchandise
				.getSmallImageURL().equals(SmallImageURL));
	}
}
