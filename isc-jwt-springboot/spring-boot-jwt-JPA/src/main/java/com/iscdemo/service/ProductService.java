package com.iscdemo.service;

import com.iscdemo.models.basemodel.InvocationContext;
import com.iscdemo.models.basemodel.MainSecurityContext;
import com.iscdemo.models.constant.ErrorConstant;
import com.iscdemo.models.entity.Product;
import com.iscdemo.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public InvocationContext<Product> fetchProductById (MainSecurityContext msc , int id) throws Exception {
        try {
            InvocationContext<Product> ic = new InvocationContext<>();
            Product products = productDao.findProductById(id);
            if(products == null) {
                return new InvocationContext<>(ErrorConstant.PRODUCT_NOT_FOUND , ErrorConstant.PRODUCT_NOT_FOUND_MESSAGE);
            }
            ic.setData(products);
            return ic;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
