package com.shoppingMall.repository;

import com.shoppingMall.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

@Controller
public interface ProductRepository extends JpaRepository<Product, Long> {
}
