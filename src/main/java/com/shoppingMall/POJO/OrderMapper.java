package com.shoppingMall.POJO;

import com.shoppingMall.model.Person;
import com.shoppingMall.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderMapper {

    private Long id;
    private Long myOrder;
    private Product product;
    private Long sum;
}
