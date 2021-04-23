package com.iscdemo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.iscdemo.models.basemodel.InvocationContext;
import com.iscdemo.models.basemodel.MainSecurityContext;
import com.iscdemo.models.constant.ErrorConstant;
import com.iscdemo.models.model.JwtRequest;
import com.iscdemo.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iscdemo.repository.UserDao;
import com.iscdemo.models.entity.User;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public InvocationContext<User> save(MainSecurityContext msc) throws Exception {
        try {
            InvocationContext<User> ic = new InvocationContext<>();
            if (userDao.findByUsername(msc.getUsername()) == null) {
                User newUser = new User();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                newUser.setFirstName(msc.getFirstName());
                newUser.setLastName(msc.getLastName());
                newUser.setRegisterDate(Long.valueOf(simpleDateFormat.format(new Date())));
                newUser.setRegisterChannel(msc.getChannel());
                newUser.setUserRole(msc.getUserRole());
                newUser.setUsername(msc.getUsername());
                newUser.setPassword(bcryptEncoder.encode(msc.getPassword()));
                ic.setData(userDao.save(newUser));

            } else {
                ic.setErrorCode(ErrorConstant.USER_ALREDY_EXIST);
                ic.setErrorMessage(ErrorConstant.USER_ALREDY_EXIST_MESSAGE);
            }
            return ic;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

    }

    public InvocationContext<String> generateAccessToken(JwtRequest jwtRequest) throws Exception {
        try {
            InvocationContext<String> ic = new InvocationContext<>();
            MainSecurityContext msc = new MainSecurityContext();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
            User user = userDao.findByUsername(jwtRequest.getUsername());
            msc.setFirstName(user.getFirstName());
            msc.setLastName(user.getLastName());
            msc.setLoginDate(Long.valueOf(simpleDateFormat.format(new Date())));
            msc.setRegisterDate(user.getRegisterDate());
            msc.setUserRole(user.getUserRole());
            msc.setId(user.getId());
            msc.setUsername(jwtRequest.getUsername());
            msc.setChannel(jwtRequest.getChannel());
            msc.setMac(jwtRequest.getMac());
            msc.setIp(jwtRequest.getIp());

            final String token = jwtTokenUtil.generateToken(msc);
            ic.setData(token);
            return ic;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}