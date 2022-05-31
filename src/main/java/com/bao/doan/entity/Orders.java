package com.bao.doan.entity;
// Generated May 18, 2020 8:38:13 PM by Hibernate Tools 4.3.5.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Order generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "orders", schema = "public")
public class Orders implements java.io.Serializable {

	private long id;
	private long userid;
	private long paymentid;
	private short daxoa;
	private String token;
	private Date ngaytao;
	private Date ngayhethan;

	public Orders() {
	}

	public Orders(long id, long userid, long paymentid, short daxoa, String token, Date ngaytao, Date ngayhethan) {
		this.id = id;
		this.userid = userid;
		this.paymentid = paymentid;
		this.daxoa = daxoa;
		this.token = token;
		this.ngaytao = ngaytao;
		this.ngayhethan = ngayhethan;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "userid", nullable = false)
	public long getUserid() {
		return this.userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	@Column(name = "paymentid", nullable = false)
	public long getPaymentid() {
		return this.paymentid;
	}

	public void setPaymentid(long paymentid) {
		this.paymentid = paymentid;
	}

	@Column(name = "daxoa", nullable = false)
	public short getDaxoa() {
		return this.daxoa;
	}

	public void setDaxoa(short daxoa) {
		this.daxoa = daxoa;
	}

	@Column(name = "token")
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ngaytao", nullable = false, length = 29)
	public Date getNgaytao() {
		return this.ngaytao;
	}

	public void setNgaytao(Date ngaytao) {
		this.ngaytao = ngaytao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ngayhethan", nullable = false, length = 29)
	public Date getNgayhethan() {
		return this.ngayhethan;
	}

	public void setNgayhethan(Date ngayhethan) {
		this.ngayhethan = ngayhethan;
	}

}