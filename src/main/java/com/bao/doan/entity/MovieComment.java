package com.bao.doan.entity;
// Generated Mar 29, 2020 8:15:32 PM by Hibernate Tools 4.3.5.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MovieComment generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "movie_comment", schema = "public")
public class MovieComment implements java.io.Serializable {

	private long id;
	private long movieid;
	private long userid;
	private String content;
	private Date ngaytao;
	private short daxoa;
	private String token;

	public MovieComment() {
	}

	public MovieComment(long id, long movieid, long userid, Date ngaytao ,short daxoa, String token) {
		this.id = id;
		this.movieid = movieid;
		this.userid = userid;
		this.ngaytao = ngaytao;
		this.daxoa = daxoa;
		this.token = token;
	}

	public MovieComment(long id, long movieid, long userid, String content, Date ngaytao ,short daxoa, String token) {
		this.id = id;
		this.movieid = movieid;
		this.userid = userid;
		this.content = content;
		this.ngaytao = ngaytao;
		this.daxoa = daxoa;
		this.token = token;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "movieid", nullable = false)
	public long getMovieid() {
		return this.movieid;
	}

	public void setMovieid(long movieid) {
		this.movieid = movieid;
	}

	@Column(name = "userid", nullable = false)
	public long getUserid() {
		return this.userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ngaytao", nullable = false, length = 29)
	public Date getNgaytao() {
		return this.ngaytao;
	}

	public void setNgaytao(Date ngaytao) {
		this.ngaytao = ngaytao;
	}

	@Column(name = "daxoa", nullable = false)
	public short getDaxoa() {
		return this.daxoa;
	}

	public void setDaxoa(short daxoa) {
		this.daxoa = daxoa;
	}
	
	@Column(name = "token", nullable = false)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
