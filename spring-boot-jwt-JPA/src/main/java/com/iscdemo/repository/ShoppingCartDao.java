package com.iscdemo.repository;

import com.iscdemo.models.entity.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartDao extends CrudRepository<ShoppingCart , Integer> {
}
