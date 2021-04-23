package com.iscdemo.service;

import com.iscdemo.models.basemodel.InvocationContext;
import com.iscdemo.models.basemodel.MainSecurityContext;
import com.iscdemo.models.entity.Order;
import com.iscdemo.models.entity.Product;
import com.iscdemo.models.entity.ShoppingCart;
import com.iscdemo.repository.ProductDao;
import com.iscdemo.repository.ShoppingCartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderManagerService {

    @Autowired
    ShoppingCartDao shoppingCartDao;

    public InvocationContext<ShoppingCart> registerOrder(MainSecurityContext msc, ShoppingCart shoppingCart) throws Exception {
        try {
            InvocationContext<ShoppingCart> ic = new InvocationContext<>();

            ic.setData(shoppingCartDao.save(shoppingCart));
            return ic;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
