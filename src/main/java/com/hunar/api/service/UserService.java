package com.hunar.api.service;


import com.hunar.api.bean.PasswordBean;
import com.hunar.api.bean.UserBean;
import com.hunar.api.exceptionHandling.util.FmkException;

import java.util.List;

public interface UserService {
	UserBean createUser(UserBean userBean) throws FmkException;
	List<UserBean> getListOfAllUsers()throws FmkException;
	UserBean getUserByIdOrUserName(String userName)throws FmkException;
	String deleteUserById(int id)throws FmkException;
	String deactivateOrActivateUser(int id, String activate)throws FmkException;
	List<String> getUserTypes();
	UserBean getUserById(int id) throws FmkException;
	String forgotPassword(PasswordBean passwordBean) throws FmkException;
	UserBean changePassword(PasswordBean passwordBean) throws FmkException;
}
