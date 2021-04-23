package com.iscdemo.service;

import com.iscdemo.models.basemodel.InvocationContext;
import com.iscdemo.models.basemodel.MainSecurityContext;
import com.iscdemo.models.entity.Product;
import com.iscdemo.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;

    public InvocationContext<Product> addProduct(MainSecurityContext msc, Product product) throws Exception {
        try {
            InvocationContext<Product> ic = new InvocationContext<>();
            ic.setData(productDao.save(product));
            return ic;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public InvocationContext<Iterable<Product>> fetchAllProduct(MainSecurityContext msc) throws Exception {
        try {
            InvocationContext<Iterable<Product>> ic = new InvocationContext<>();
            Iterable<Product> products = productDao.findAll();
            ic.setData(products);
            return ic;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

}
