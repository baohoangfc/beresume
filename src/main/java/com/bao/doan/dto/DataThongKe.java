package com.bao.doan.dto;

public class DataThongKe {
	private String name;
	private long count;

	public DataThongKe() {
		super();
	}

	public DataThongKe(String name, long count) {
		super();
		this.name = name;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
