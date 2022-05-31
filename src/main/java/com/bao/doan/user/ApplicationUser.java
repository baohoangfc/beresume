package com.bao.doan.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "application_user", schema = "public")
public class ApplicationUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String username;
	private String password;
	private String system;
	private short isdefaultpassword;
	private String token;

	public ApplicationUser(long id, String username, String password, String system, short isdefaultpassword,
			String token) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.system = system;
		this.isdefaultpassword = isdefaultpassword;
		this.token = token;
	}

	public ApplicationUser() {
		super();
	}
	
	@Column(name = "token", nullable = false)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "system")
	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	@Column(name = "isdefaultpassword")
	public short getIsdefaultpassword() {
		return isdefaultpassword;
	}

	public void setIsdefaultpassword(short isdefaultpassword) {
		this.isdefaultpassword = isdefaultpassword;
	}

}
