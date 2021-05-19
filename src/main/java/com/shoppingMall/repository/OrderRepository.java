package com.shoppingMall.repository;

import com.shoppingMall.model.Order;
import com.shoppingMall.model.Person;
import com.shoppingMall.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOrderByPersonAndProduct(Person person, Product product);
}
