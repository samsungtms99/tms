package com.hunar.api.login;


import com.hunar.api.bean.UserBean;

public class LoginResponse {
	private String token;
	private String type = "Bearer";
	private String username;
	private String email;
	private UserBean userBean;

	public LoginResponse(String token, String type, String username, String email, UserBean userBean) {
		super();
		this.token = token;
		this.type = type;
		this.username = username;
		this.email = email;
		this.userBean = userBean;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
}
