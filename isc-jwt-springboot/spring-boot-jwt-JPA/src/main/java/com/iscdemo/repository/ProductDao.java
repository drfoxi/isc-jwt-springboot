package com.iscdemo.repository;

import com.iscdemo.models.entity.Product;
import com.iscdemo.models.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends CrudRepository<Product, Integer> {

    Product findProductById(Integer Id);
    Product findById(int id);
}
