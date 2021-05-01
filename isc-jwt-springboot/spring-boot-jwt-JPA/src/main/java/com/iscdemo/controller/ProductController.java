package com.iscdemo.controller;

import com.iscdemo.models.basemodel.InvocationContext;
import com.iscdemo.models.basemodel.MainSecurityContext;
import com.iscdemo.models.entity.Product;
import com.iscdemo.models.entity.User;
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

    @RequestMapping(value = "/fetchProductById", method = RequestMethod.GET)
    public ResponseEntity<InvocationContext> fetchAllProduct(@RequestParam(name = "id") Integer id,
                                                             @RequestHeader(name = "Authorization") String token)
            throws Exception {
        MainSecurityContext msc = jwtTokenUtil.getMainSecurityContextFromToken(token);
        return ResponseEntity.ok(productService.fetchProductById(msc , id));
    }

    @RequestMapping(value = "/updateProduct", method = RequestMethod.PUT)
    public ResponseEntity<InvocationContext> fetchById(@RequestBody Product product,
                                                       @RequestHeader(name = "Authorization") String token)
            throws Exception {
        MainSecurityContext msc = jwtTokenUtil.getMainSecurityContextFromToken(token);
        return ResponseEntity.ok(productService.updateProduct(msc , product));
    }

    @RequestMapping(value = "/deleteProductById", method = RequestMethod.DELETE)
    public ResponseEntity<InvocationContext> deleteUserById(@RequestParam(name = "id") int id,
                                                            @RequestHeader(name = "Authorization") String token)
            throws Exception {
        MainSecurityContext msc = jwtTokenUtil.getMainSecurityContextFromToken(token);
        return ResponseEntity.ok(productService.deleteProductById(msc , id));
    }
}
