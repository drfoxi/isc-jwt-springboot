package com.iscdemo.controller;

import com.iscdemo.models.basemodel.InvocationContext;
import com.iscdemo.models.basemodel.MainSecurityContext;
import com.iscdemo.models.entity.Product;
import com.iscdemo.security.JwtTokenUtil;
import com.iscdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/product"})
@CrossOrigin
public class ProductController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/insertProduct", method = RequestMethod.POST)
    public ResponseEntity<InvocationContext> addProduct(@RequestBody Product product,
                                                        @RequestHeader(name = "Authorization") String token)
            throws Exception {
        MainSecurityContext msc = jwtTokenUtil.getMainSecurityContextFromToken(token);
        return ResponseEntity.ok(productService.addProduct(msc, product));
    }

    @RequestMapping(value = "/fetchAllProduct", method = RequestMethod.GET)
    public ResponseEntity<InvocationContext> fetchAllProduct(@RequestHeader(name = "Authorization") String token)
            throws Exception {
        MainSecurityContext msc = jwtTokenUtil.getMainSecurityContextFromToken(token);
        return ResponseEntity.ok(productService.fetchAllProduct(msc));
    }

}
