package com.bao.doan.dto;

import java.util.List;

public class DataMovie {
	private long userid;
	private List<String> movieid;
	private List<String> rating;

	public DataMovie() {
		super();
	}

	public DataMovie(long userid, List<String> movieid, List<String> rating) {
		super();
		this.userid = userid;
		this.movieid = movieid;
		this.rating = rating;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public List<String> getMovieid() {
		return movieid;
	}

	public void setMovieid(List<String> movieid) {
		this.movieid = movieid;
	}

	public List<String> getRating() {
		return rating;
	}

	public void setRating(List<String> rating) {
		this.rating = rating;
	}

}
