package com.iscdemo.controller;

import com.iscdemo.models.basemodel.InvocationContext;
import com.iscdemo.models.basemodel.MainSecurityContext;
import com.iscdemo.models.entity.User;
import com.iscdemo.security.JwtTokenUtil;
import com.iscdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;

@RestController
@RequestMapping({"/user"})
@CrossOrigin
public class UserController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/fetchAllUsers", method = RequestMethod.GET)
    public ResponseEntity<InvocationContext> fetchAllUsers(@RequestHeader(name = "Authorization") String token)
            throws Exception {
        MainSecurityContext msc = jwtTokenUtil.getMainSecurityContextFromToken(token);
        return ResponseEntity.ok(userService.fetchAllUsers(msc));
    }

    @RequestMapping(value = "/fetchUserById", method = RequestMethod.GET)
    public ResponseEntity<InvocationContext> fetchById(@RequestParam(name = "id") Integer id,
                                                       @RequestHeader(name = "Authorization") String token)
            throws Exception {
        MainSecurityContext msc = jwtTokenUtil.getMainSecurityContextFromToken(token);
        return ResponseEntity.ok(userService.fetchUserById(msc , id));
    }

    @RequestMapping(value = "/updateUserById", method = RequestMethod.PUT)
    public ResponseEntity<InvocationContext> fetchById(@RequestBody User user,
                                                       @RequestHeader(name = "Authorization") String token)
            throws Exception {
        MainSecurityContext msc = jwtTokenUtil.getMainSecurityContextFromToken(token);
        return ResponseEntity.ok(userService.updateUser(msc , user));
    }


    @RequestMapping(value = "/deleteUserById", method = RequestMethod.DELETE)
    public ResponseEntity<InvocationContext> deleteUserById(@RequestParam(name = "id") Long id,
                                                       @RequestHeader(name = "Authorization") String token)
            throws Exception {
        MainSecurityContext msc = jwtTokenUtil.getMainSecurityContextFromToken(token);
        return ResponseEntity.ok(userService.deleteUserById(msc , id));
    }
}
