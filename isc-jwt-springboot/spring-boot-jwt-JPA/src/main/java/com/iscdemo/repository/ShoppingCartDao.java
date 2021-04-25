package com.iscdemo.repository;

import com.iscdemo.models.entity.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ShoppingCartDao extends CrudRepository<ShoppingCart , Integer> {

    ShoppingCart findByTrackingCode(Long trackingCode);
}
