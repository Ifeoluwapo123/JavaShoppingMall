package com.shoppingMall.repository;

import com.shoppingMall.model.Order;
import com.shoppingMall.model.Person;
import com.shoppingMall.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOrderByPersonAndProduct(Person person, Product product);
    void deleteAllByProductId(Long productId);
    List<Order> findAllByPersonId(Long personId);
}
