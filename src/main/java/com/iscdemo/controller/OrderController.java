package com.iscdemo.controller;

import com.iscdemo.models.basemodel.InvocationContext;
import com.iscdemo.models.basemodel.MainSecurityContext;
import com.iscdemo.models.entity.Order;
import com.iscdemo.models.entity.Product;
import com.iscdemo.models.entity.ShoppingCart;
import com.iscdemo.security.JwtTokenUtil;
import com.iscdemo.service.OrderManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/order"})
@CrossOrigin
public class OrderController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    OrderManagerService orderManagerService;

    @RequestMapping(value = "/registerOrder", method = RequestMethod.POST)
    public ResponseEntity<InvocationContext> registerOrder(@RequestBody ShoppingCart shoppingCart,
                                                           @RequestHeader(name = "Authorization") String token)
            throws Exception {
        MainSecurityContext msc = jwtTokenUtil.getMainSecurityContextFromToken(token);
        return ResponseEntity.ok(orderManagerService.registerOrder(msc, shoppingCart));
    }

}
