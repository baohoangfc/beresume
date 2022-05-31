package com.bao.doan.dto;

import java.util.Date;

public class DataComment {
	private long userid;
	private long movieid;
	private String hoten;
	private String content;
	private Date ngaytao;

	public DataComment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataComment(long userid, long movieid, String hoten, String content, Date ngaytao) {
		super();
		this.userid = userid;
		this.movieid = movieid;
		this.hoten = hoten;
		this.content = content;
		this.ngaytao = ngaytao;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getMovieid() {
		return movieid;
	}

	public void setMovieid(long movieid) {
		this.movieid = movieid;
	}

	public String getHoten() {
		return hoten;
	}

	public void setHoten(String hoten) {
		this.hoten = hoten;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getNgaytao() {
		return ngaytao;
	}

	public void setNgaytao(Date ngaytao) {
		this.ngaytao = ngaytao;
	}

}
