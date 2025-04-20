package com.hunar.api.bean;

import com.hunar.api.generic.bean.GenericBean;

public class UserBean extends GenericBean {

	private int userId;
	private String userName;
	private String userEmail;
	private String firstName;
	private String lastName;
	private String userPassword;
	private String isActive;
	private String isAdmin;
	private String roles;
	private String mobileNo;
	private String viewPassword;

	public UserBean() {
		super();
	}

	public UserBean(int userId, String viewPassword, String userName, String userEmail, String firstName, String lastName, String userPassword, String isAdmin, String isActive, String roles, String mobileNo) {
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userPassword = userPassword;
		this.isAdmin = isAdmin;
		this.isActive = isActive;
		this.roles = roles;
		this.mobileNo = mobileNo;
		this.viewPassword = viewPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getViewPassword() {
		return viewPassword;
	}

	public void setViewPassword(String viewPassword) {
		this.viewPassword = viewPassword;
	}
}
