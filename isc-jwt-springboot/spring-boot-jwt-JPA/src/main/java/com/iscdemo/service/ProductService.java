package com.iscdemo.service;

import com.iscdemo.models.basemodel.InvocationContext;
import com.iscdemo.models.basemodel.MainSecurityContext;
import com.iscdemo.models.constant.ErrorConstant;
import com.iscdemo.models.entity.Product;
import com.iscdemo.models.entity.User;
import com.iscdemo.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

    public InvocationContext<Product> updateProduct(MainSecurityContext msc, Product product) throws Exception {
        InvocationContext<Product> ic = new InvocationContext<>();
        Product newProduct = productDao.findProductById(product.getId());
        if (newProduct == null) {
            return new InvocationContext<>(ErrorConstant.PRODUCT_NOT_FOUND, ErrorConstant.PRODUCT_NOT_FOUND_MESSAGE);
        }
        newProduct.setProductName(product.getProductName());
        newProduct.setPreparationTime(product.getPreparationTime());
        newProduct.setProductPrice(product.getProductPrice());
        newProduct.setAvailable(product.isAvailable());
        newProduct.setDescription(product.getDescription());

        ic.setData(productDao.save(newProduct));
        return ic;
    }

    public InvocationContext<Product> deleteProductById(MainSecurityContext msc, int id) throws Exception {
        InvocationContext<Product> ic = new InvocationContext<>();
        Product product = productDao.findById(id);
        if (product == null) {
            return new InvocationContext<>(ErrorConstant.PRODUCT_NOT_FOUND, ErrorConstant.PRODUCT_NOT_FOUND_MESSAGE);
        }
        productDao.delete(product);
        ic.setData(product);
        return ic;
    }


}
