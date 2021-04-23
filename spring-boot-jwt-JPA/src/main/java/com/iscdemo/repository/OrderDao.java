package com.iscdemo.repository;

import com.iscdemo.models.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends CrudRepository<Order , Integer> {
}
