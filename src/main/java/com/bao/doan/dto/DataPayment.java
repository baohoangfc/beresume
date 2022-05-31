package com.bao.doan.dto;

import java.util.Date;

public class DataPayment {
	private long userid;
	private long paymentid;
	private String username;
	private String email;
	private String namepayment;
	private double amount;
	private Date ngaytao;
	private Date ngayhethan;

	public DataPayment() {
		super();
	}

	public DataPayment(long userid, long paymentid, String username, String email, String namepayment, double amount,
			Date ngaytao, Date ngayhethan) {
		super();
		this.userid = userid;
		this.paymentid = paymentid;
		this.username = username;
		this.email = email;
		this.namepayment = namepayment;
		this.amount = amount;
		this.ngaytao = ngaytao;
		this.ngayhethan = ngayhethan;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(long paymentid) {
		this.paymentid = paymentid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNamepayment() {
		return namepayment;
	}

	public void setNamepayment(String namepayment) {
		this.namepayment = namepayment;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getNgaytao() {
		return ngaytao;
	}

	public void setNgaytao(Date ngaytao) {
		this.ngaytao = ngaytao;
	}

	public Date getNgayhethan() {
		return ngayhethan;
	}

	public void setNgayhethan(Date ngayhethan) {
		this.ngayhethan = ngayhethan;
	}

}
