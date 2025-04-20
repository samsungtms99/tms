package com.hunar.api.service.impl;


import com.hunar.api.bean.PasswordBean;
import com.hunar.api.bean.UserBean;
import com.hunar.api.entity.UserEntity;
import com.hunar.api.entity.UserType;
import com.hunar.api.exceptionHandling.util.Errors;
import com.hunar.api.exceptionHandling.util.FmkException;
import com.hunar.api.repository.UserRepository;
import com.hunar.api.repository.UserTypeRepository;
import com.hunar.api.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	public static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserTypeRepository userTypeRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserBean createUser(UserBean userBean) throws FmkException {
		if (userBean != null && userBean.getUserId()!=0){
			logger.info("Updating user: "+userBean.getUserName());
			Optional<UserEntity> userEntity = userRepository.findById(userBean.getUserId());
			if (!userEntity.isPresent()){
				logger.info("User does not exists with UserName: "+userBean.getUserName());
				throw  new FmkException("U1001",  "Invalid user Id: "+ String.valueOf( userBean.getUserId()));
			}
			userEntity.get().setUserId(userBean.getUserId());
			userEntity.get().setUserEmail(userBean.getUserEmail());
			userEntity.get().setRoles(userBean.getRoles());
			userEntity.get().setFirstName(userBean.getFirstName());
			userEntity.get().setLastName(userBean.getLastName());
			userEntity.get().setIsActive(userBean.getIsActive());
			userEntity.get().setIsAdmin(userBean.getIsAdmin());
			userEntity.get().setMobileNo(userBean.getMobileNo());
			UserEntity userEntity1 = userRepository.save(userEntity.get());
			logger.info("Updated user successfully: "+userBean.getUserName());
			UserBean userBean1 = new UserBean();
			BeanUtils.copyProperties(userEntity1,userBean1);
			return userBean1;

		}else {
			logger.info("Creating new user: "+userBean.getUserName());
			UserEntity userEntity = new UserEntity();
			BeanUtils.copyProperties(userBean,userEntity);
//			String username = userBean.getUserName().toLowerCase();
//			userEntity.setUserName(username);
			userEntity.setViewPassword(userBean.getUserPassword());
			userEntity.setUserPassword(encoder.encode(userBean.getUserPassword()));
			UserEntity userEntity1 = userRepository.save(userEntity);
			logger.info("Created new user successfully: "+userBean.getUserName());
			UserBean userBean1 = new UserBean();
			BeanUtils.copyProperties(userEntity1,userBean1);
			return userBean1;
		}
	}

	@Override
	public List<UserBean> getListOfAllUsers() throws FmkException {
		List<UserBean> listOfAllUsersBean = null;
		List<UserEntity> listOfAllUsersEntity = (List<UserEntity>) userRepository.findAll();
		if (!listOfAllUsersEntity.isEmpty()){
			listOfAllUsersBean = new ArrayList<>();
			for (UserEntity userEntity : listOfAllUsersEntity){
				UserBean userBean = new UserBean();
				BeanUtils.copyProperties(userEntity,userBean);
				listOfAllUsersBean.add(userBean);
			}
			return listOfAllUsersBean;
		}else {
			return  new ArrayList<>();
		}
	}

	@Override
	public UserBean getUserByIdOrUserName(String userName) throws FmkException {
		if (userName == null){
			throw  new FmkException("U1001", "Invalid userame: "+ userName);
		}
		UserEntity userEntity = userRepository.findByUserName(userName).get();
		if (userEntity== null){
			throw  new FmkException("U1001",  "Invalid userame: "+ userName);
		}else {
			UserBean userBean = new UserBean();
			BeanUtils.copyProperties(userEntity,userBean);
			return userBean;
		}

    }

	@Override
	public String deleteUserById(int id) throws FmkException {
		if (id == 0){
			logger.info("Invalid user ID: "+id);
			throw  new FmkException("U1002","Invalid user Id: "+ String.valueOf(id));
		}
		userRepository.deleteById(id);
		return "User deleted successfully..";

	}

	@Override
	public String deactivateOrActivateUser(int id, String activate) throws FmkException {
		if (id != 0 && activate !=null){
			Optional<UserEntity> userEntity = userRepository.findById(id);
			if (!userEntity.isPresent()){
				logger.info("Invalid user ID: "+id);
				throw  new FmkException("U1002", "Invalid user Id: "+ String.valueOf(id));
			}
			userEntity.get().setIsActive(activate);
			userRepository.save(userEntity.get());
			logger.info("User active status updated to: "+ activate);
			return "User "+activate+" successfully";
		}
		return "";
	}


	@Override
	public List<String> getUserTypes() {
		List<UserType> userTypeList = (List<UserType>) userTypeRepository.findAll();
        return userTypeList.stream()
                .map(UserType::getTypeName) // Extracts typeName from each UserType
                .collect(Collectors.toList());
	}

	@Override
	public UserBean getUserById(int id) throws FmkException {
		if (id == 0){
			throw  new FmkException("U1002", "Invalid user Id: "+ String.valueOf(id));
		}
		UserEntity userEntity = userRepository.findById(id).get();
		if (userEntity== null){
			throw  new FmkException("U1002", "Invalid user Id: "+ String.valueOf(id));
		}else {
			UserBean userBean = new UserBean();
			BeanUtils.copyProperties(userEntity,userBean);
			return userBean;
		}
	}

	@Override
	public String forgotPassword(PasswordBean passwordBean) throws FmkException {
		if (passwordBean != null && passwordBean.getMobile()!= null){
			UserEntity userEntity = userRepository.findByMobileNo(passwordBean.getMobile());
			if (userEntity==null){
				logger.info("Incorret mobile number: "+passwordBean.getMobile());
				throw  new FmkException("U1006", "Incorret mobile number: "+passwordBean.getMobile());
			}
			if (passwordBean.getNewPassword().matches(passwordBean.getConfirmPassword())){
				userEntity.setViewPassword(passwordBean.getNewPassword());
				userEntity.setUserPassword(encoder.encode(passwordBean.getNewPassword()));
				userRepository.save(userEntity);
				return "Password updated successfully!";
			}else {
				logger.info("password mismatched");
				throw  new FmkException("U1004", "confirm password mismatched");
			}

		}
		return null;
	}

	@Override
	public UserBean changePassword(PasswordBean passwordBean) throws FmkException {
		if (passwordBean!= null && passwordBean.getUserId() !=0){
			Optional<UserEntity> userEntity = userRepository.findById(passwordBean.getUserId());
			if (!userEntity.isPresent()){
				logger.info("Invalid user Id: "+ passwordBean.getUserId());
				throw  new FmkException("U1002", "Invalid user Id: "+ passwordBean.getUserId());
			}
			if (!encoder.matches(passwordBean.getOldPassword(),userEntity.get().getUserPassword())){
				logger.info("Incorrect old password");
				throw  new FmkException("U1004", "Incorrect old password");
			}
			if (passwordBean.getNewPassword().matches(passwordBean.getConfirmPassword())){
				userEntity.get().setViewPassword(passwordBean.getNewPassword());
				userEntity.get().setUserPassword(encoder.encode(passwordBean.getNewPassword()));
				userRepository.save(userEntity.get());
				UserBean userBean = new UserBean();
				BeanUtils.copyProperties(userEntity.get(), userBean);
				return userBean;
			}else {
				logger.info("password mismatched");
				throw  new FmkException("U1005", "confirm password mismatched");
			}

		}
		return  null;
	}
}
