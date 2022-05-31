package com.bao.doan.dto;

public class DataUserRated {
	private long id;
	private String title;
	private String poster;
	private double rating;

	public DataUserRated() {
		super();
	}

	public DataUserRated(long id, String title, String poster, double rating) {
		super();
		this.id = id;
		this.title = title;
		this.poster = poster;
		this.rating = rating;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

}
