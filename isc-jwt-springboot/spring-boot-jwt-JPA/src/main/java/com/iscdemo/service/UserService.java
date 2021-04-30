package com.iscdemo.service;


import com.iscdemo.models.basemodel.InvocationContext;
import com.iscdemo.models.basemodel.MainSecurityContext;
import com.iscdemo.models.constant.ErrorConstant;
import com.iscdemo.models.constant.ProjectConstant;
import com.iscdemo.models.entity.Product;
import com.iscdemo.models.entity.User;
import com.iscdemo.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public InvocationContext<Iterable<User>> fetchAllUsers(MainSecurityContext msc) throws Exception {
        try {
            InvocationContext<Iterable<User>> ic = new InvocationContext<>();
            InvocationContext<Boolean> isAdminAccessValidatorIc = isAdmin(msc);
            if (!isAdminAccessValidatorIc.isSuccessful()) {
                return new InvocationContext<>(isAdminAccessValidatorIc.getErrorCode(), isAdminAccessValidatorIc.getErrorMessage());
            }
            ic.setData(userDao.findAll());
            return ic;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }


    public InvocationContext<User> fetchUserById(MainSecurityContext msc, int id) throws Exception {
        InvocationContext<User> ic = new InvocationContext<>();
        InvocationContext<Boolean> isAdminAccessValidatorIc = isAdmin(msc);
        if (!isAdminAccessValidatorIc.isSuccessful()) {
            return new InvocationContext<>(isAdminAccessValidatorIc.getErrorCode(), isAdminAccessValidatorIc.getErrorMessage());
        }
        User user = userDao.findById(id);
        if (user == null) {
            return new InvocationContext<>(ErrorConstant.USER_NOT_FOUND, ErrorConstant.USER_NOT_FOUND_MESSAGE);
        }
        ic.setData(user);
        return ic;
    }

    public InvocationContext<User> updateUser(MainSecurityContext msc, User user) throws Exception {
        InvocationContext<User> ic = new InvocationContext<>();
        InvocationContext<Boolean> isAdminAccessValidatorIc = isAdmin(msc);
        User currentUser = userDao.findByUsername(user.getUsername());
        if (!isAdminAccessValidatorIc.isSuccessful()) {
            return new InvocationContext<>(isAdminAccessValidatorIc.getErrorCode(), isAdminAccessValidatorIc.getErrorMessage());
        }
        if (currentUser != null && currentUser.getId() != user.getId()) {
            return new InvocationContext<>(ErrorConstant.USER_ALREDY_EXIST, ErrorConstant.USER_ALREDY_EXIST_MESSAGE);
        }
        User newUser = userDao.findById(user.getId());
        if (newUser == null) {
            return new InvocationContext<>(ErrorConstant.USER_NOT_FOUND, ErrorConstant.USER_NOT_FOUND_MESSAGE);
        }
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUsername(user.getUsername());
        if (user.getPassword() != null) {
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        }
        ic.setData(userDao.save(newUser));
        return ic;
    }


    public InvocationContext<User> deleteUserById(MainSecurityContext msc, Long id) throws Exception {
        InvocationContext<User> ic = new InvocationContext<>();
        InvocationContext<Boolean> isAdminAccessValidatorIc = isAdmin(msc);
        if (!isAdminAccessValidatorIc.isSuccessful()) {
            return new InvocationContext<>(isAdminAccessValidatorIc.getErrorCode(), isAdminAccessValidatorIc.getErrorMessage());
        }
        User user = userDao.findById(id);
        if (user == null) {
            return new InvocationContext<>(ErrorConstant.USER_NOT_FOUND, ErrorConstant.USER_NOT_FOUND_MESSAGE);
        }
        userDao.delete(user);
        ic.setData(user);
        return ic;
    }

    private InvocationContext<Boolean> isAdmin(MainSecurityContext msc) {
        InvocationContext<Boolean> ic = new InvocationContext<>();
        if (userDao.findByUsername(msc.getUsername()).getUserRole() != ProjectConstant.ADMIN_USER_CODE) {
            return new InvocationContext<>(ErrorConstant.ACCESS_DENIED, ErrorConstant.ACCESS_DENIED_MESSAGE);
        }
        ic.setData(true);
        return ic;
    }
}

