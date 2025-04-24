package com.hunar.api.controller;

import com.hunar.api.bean.PasswordBean;
import com.hunar.api.bean.UserBean;
import com.hunar.api.entity.UserEntity;
import com.hunar.api.exceptionHandling.util.FmkException;
import com.hunar.api.login.LoginResponse;
import com.hunar.api.repository.UserRepository;
import com.hunar.api.request.AuthRequest;
import com.hunar.api.security.JwtService;
import com.hunar.api.security.UserInfoService;
import com.hunar.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/loginUser")
    public LoginResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws FmkException {
        authRequest.setUsername(authRequest.getUsername().toLowerCase());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            UserBean userBean = userService.getUserByIdOrUserName(authRequest.getUsername());
            String token = jwtService.generateToken(authRequest.getUsername());
            UserEntity userEntity = userRepository.findByUserName(authRequest.getUsername()).get();
            if (userEntity != null) {
                userEntity.setSecretToken(token);
                userRepository.save(userEntity);
            }
            return new LoginResponse(token, "Bearer", userBean.getUserName(), userBean.getUserEmail(), userBean);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestBody PasswordBean passwordBean) throws FmkException {
        return userService.forgotPassword(passwordBean);
    }

    @PostMapping(value = "/registerUser")
    public UserBean createUser(@RequestBody UserBean userBean) throws FmkException {
        if (userBean != null) {
           return userService.createUser(userBean);
        }
        return userBean;
    }

}
