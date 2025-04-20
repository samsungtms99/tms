package com.hunar.api.repository;

import com.hunar.api.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	@Query("FROM UserEntity WHERE userName=:userName and userPassword=:userPassword")
	UserEntity validateLogin(String userName, String userPassword);

	@Query("FROM UserEntity where isAdmin ='Y'")
	List<UserEntity> fetchAdmins();

	Optional<UserEntity> findByUserName(String username);

    Optional<UserEntity> findByUserEmail(String email);

    UserEntity findByMobileNo(String mobile);

	UserEntity findBySecretToken(String jwt);

    List<UserEntity> findAllByRoles(String roleAdmin);
}
