package com.shoppingMall.service;

import com.shoppingMall.model.Order;
import com.shoppingMall.model.Person;
import com.shoppingMall.model.Product;
import com.shoppingMall.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Order getOrder(Person person, Product product){
        Order order = null;

        try {
            order = orderRepository.findOrderByPersonAndProduct(person, product);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return order;
    }

    public boolean addOrder(Person person, Product product){
        boolean flag = false;

        try {
            Order order = orderRepository.findOrderByPersonAndProduct(person, product);

            if(order == null){
                Order newOrder = new Order();
                Long count = 1L;
                newOrder.setMyOrder(count);
                newOrder.setPerson(person);
                newOrder.setProduct(product);

                orderRepository.save(newOrder);

            }else {
                order.setMyOrder(order.getMyOrder() + 1);
                order.setPerson(person);
                order.setProduct(product);

                orderRepository.save(order);
            }

            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }
}
