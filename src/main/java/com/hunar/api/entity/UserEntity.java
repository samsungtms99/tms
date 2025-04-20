package com.hunar.api.entity;


import com.hunar.api.generic.entity.GenericEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "TM_USERS")
public class UserEntity extends GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_USER")
	private int userId;

	@Column(name = "NAME_USER", unique = true)
	private String userName;

	@Column(name = "NAME_EMAIL", unique = true)
	private String userEmail;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "PASSWORD_USER")
	private String userPassword;

	@Column(name = "VIEW_PASSWORD")
	private String viewPassword;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "IS_ADMIN")
	private String isAdmin;

	@Column(name = "USER_TYPE")
	private String roles;

	@Column(name = "TOKEN")
	private String secretToken;

	@Column(name = "MOBILE_NO")
	private String mobileNo;

	private String gender;

	public UserEntity() {
		super();
	}

	public UserEntity(int userId, String userName, String userEmail, String firstName, String lastName, String userPassword, String viewPassword, String isActive, String isAdmin, String roles, String secretToken, String mobileNo, String gender) {
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userPassword = userPassword;
		this.viewPassword = viewPassword;
		this.isActive = isActive;
		this.isAdmin = isAdmin;
		this.roles = roles;
		this.secretToken = secretToken;
		this.mobileNo = mobileNo;
		this.gender = gender;
	}

	public String getViewPassword() {
		return viewPassword;
	}

	public void setViewPassword(String viewPassword) {
		this.viewPassword = viewPassword;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getSecretToken() {
		return secretToken;
	}

	public void setSecretToken(String secretToken) {
		this.secretToken = secretToken;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
