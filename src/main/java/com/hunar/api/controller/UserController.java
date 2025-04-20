package com.hunar.api.controller;

import com.hunar.api.bean.PasswordBean;
import com.hunar.api.bean.UserBean;
import com.hunar.api.entity.CompanyEntity;
import com.hunar.api.entity.UserEntity;
import com.hunar.api.exceptionHandling.util.FmkException;
import com.hunar.api.login.LoginResponse;
import com.hunar.api.repository.UserRepository;
import com.hunar.api.request.AuthRequest;
import com.hunar.api.security.JwtService;
import com.hunar.api.security.UserInfoService;
import com.hunar.api.service.CompanyService;
import com.hunar.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/user")
public class UserController {
//    @Autowired
//    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/createUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserBean createUser(@RequestBody UserBean userBean) throws FmkException {
        if (userBean != null) {
          return  userService.createUser(userBean);
        }
        return null;
    }

    @GetMapping(value = "/getListOfAllUsers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    List<UserBean> getListOfAllUsers() throws FmkException {
        return userService.getListOfAllUsers();
    }

    @GetMapping(value = "/getUserByUserName/{userName}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPERVISOR')")
    UserBean getUserByUserName(@PathVariable("userName") String userName) throws FmkException {
        return userService.getUserByIdOrUserName(userName);
    }

    @DeleteMapping(value = "/deleteUserById/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    String deleteUserById(@PathVariable("id") int id) throws FmkException {
       return userService.deleteUserById(id);
    }

    @PutMapping(value = "/deactivateOrActivateUser/{id}/{activate}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    String deactivateOrActivateUser(@PathVariable("id") int id, @PathVariable("activate") String activate) throws FmkException {
       return userService.deactivateOrActivateUser(id, activate);
    }

    @GetMapping(value = "/getUserById/{id}")
    UserBean getUserByUserName(@PathVariable("id") int id) throws FmkException {
        return userService.getUserById(id);
    }


    @GetMapping(value = "/getUserTypes")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    List<String> getUserTypes() {
        return userService.getUserTypes();
    }

    @PostMapping("/changePassword")
    public UserBean changePassword(@RequestBody PasswordBean passwordBean) throws FmkException {
        return userService.changePassword(passwordBean);
    }

    @Autowired
    CompanyService companyService;

    @PostMapping(value = "/createComapany")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CompanyEntity createComapany(@RequestBody CompanyEntity companyEntity) {
        if (companyEntity != null) {
            return  companyService.createCompany(companyEntity);
        }
        return null;
    }
}
