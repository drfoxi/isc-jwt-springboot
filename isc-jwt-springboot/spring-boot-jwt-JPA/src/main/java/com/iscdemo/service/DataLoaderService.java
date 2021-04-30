package com.iscdemo.service;

import com.iscdemo.models.constant.ProjectConstant;
import com.iscdemo.models.entity.User;
import com.iscdemo.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DataLoaderService implements ApplicationRunner {

    @Autowired
    UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createAdminUserIfNotExcite();
    }

    private void createAdminUserIfNotExcite() {
        if (userDao.findByUserRole(ProjectConstant.ADMIN_USER_CODE).size() == 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            User adminUser = new User();
            adminUser.setFirstName("Admin");
            adminUser.setLastName("");
            adminUser.setRegisterDate(Long.valueOf(simpleDateFormat.format(new Date())));
            adminUser.setRegisterChannel(60);
            adminUser.setUserRole(1);
            adminUser.setUsername("admin");
            adminUser.setPassword(bcryptEncoder.encode("admin"));
            userDao.save(adminUser);
        }
    }
}
