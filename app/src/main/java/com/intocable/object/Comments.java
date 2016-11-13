package com.intocable.object;

public class Comments {

	private String Author;
	private String Comments;
	

	public Comments() {
		super();
	}

	public Comments(String author, String comment) {
		super();
		setAuthor(author);
		setComments(comment);
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getComments() {
		return Comments;
	}

	public void setComments(String comments) {
		Comments = comments;
	}


}
