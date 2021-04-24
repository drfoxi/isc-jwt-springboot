package com.iscdemo.controller;

import com.iscdemo.models.entity.Product;
import com.iscdemo.repository.OrderDao;
import com.iscdemo.repository.ProductDao;
import com.iscdemo.repository.ShoppingCartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/order"})
public class HelloWorldController {

	@Autowired
	ProductDao productDao;

	@Autowired
	OrderDao orderDao;

	@Autowired
	ShoppingCartDao shoppingCartDao;

	@RequestMapping({ "/hello" })
	public String testService() {

		productDao.findAll();
		orderDao.findAll();
		shoppingCartDao.findAll();

		return "Hello World";
	}

}