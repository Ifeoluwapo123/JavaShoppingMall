package com.shoppingMall.service;

import com.shoppingMall.POJO.OrderMapper;
import com.shoppingMall.model.Order;
import com.shoppingMall.model.Product;
import com.shoppingMall.repository.OrderRepository;
import com.shoppingMall.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * CREATE operation on Product
     * @param file
     * @param product
     * @return boolean
     * */
    public boolean saveProduct(MultipartFile file, Product product){

        boolean flag = false;

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        //Extract the image extension
        String ext = fileName.substring(fileName.indexOf(".")+1);

        //set image extension
        product.setImageExt(ext);

        if(fileName.isEmpty()){
            System.out.println("Not a valid file");
        }

        try {

            product.setImage("data:image/"+ext+";base64,"+Base64.getEncoder().encodeToString(file.getBytes()));

            productRepository.save(product);

            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * GET operation on Product
     * @return list of the products
     * */
    public List<Product> getProducts(){
        List<Product> product = new ArrayList<>();

        try {
            product = productRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }

    /**
     * GET operation on Product
     * @param productId
     * @return product by its id
     * */
    public Product getProductById(Long productId){
        Product product = null;

        try {
            product = productRepository.findById(productId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }

    /**
     * CREATE operation on Product
     * @param file
     * @return boolean
     * */
    public boolean editProduct(MultipartFile file, Product product){
        boolean flag = false;

         try{
             Product editProduct = productRepository.findById(product.getId()).get();

             editProduct.setCategory(product.getCategory());
             editProduct.setPrice(product.getPrice());
             editProduct.setQuantity(product.getQuantity());
             editProduct.setName(product.getName());
             editProduct.setDescription(product.getDescription());

             //check if image was also edited
             String fileName = StringUtils.cleanPath(file.getOriginalFilename());

             if(!fileName.isEmpty()){
                 //Extract the image extension
                 String ext = fileName.substring(fileName.indexOf(".")+1);

                 //set image extension
                 editProduct.setImageExt(ext);
                 editProduct.setImage("data:image/"+ext+";base64,"+Base64.getEncoder().encodeToString(file.getBytes()));
             }

             productRepository.save(editProduct);
             flag = true;
         }catch (Exception e) {
             e.printStackTrace();
         }

        return flag;
    }

    /**
     * DELETE operation on Product
     * @param productId
     * @return boolean(true for successful update and false on failure on post)
     * */
    public boolean deleteProduct(Long productId){
        boolean flag = false;

        try {

            orderRepository.deleteAllByProductId(productId);

            productRepository.deleteById(productId);

            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * GET operation on Comment
     * to get all orders made by a person
     * @param personId
     * @return list
     * */
    public List<OrderMapper> getOrdersOnProductBy(Long personId){
        List<OrderMapper> listOfOrders = new ArrayList<>();

        try {
            List<Order> orders = orderRepository.findAllByPersonId(personId);

            orders
                .forEach(order -> {
                    OrderMapper orderMapper = new OrderMapper();

                    orderMapper.setMyOrder(order.getMyOrder());
                    orderMapper.setId(order.getId());

                    Product product = productRepository.findById(order.getProduct().getId()).get();

                    orderMapper.setProduct(product);

                    Long sum = order.getMyOrder() * product.getPrice();

                    orderMapper.setSum(sum);

                    listOfOrders.add(orderMapper);
                });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfOrders;
    }
}