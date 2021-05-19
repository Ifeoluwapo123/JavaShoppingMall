package com.shoppingMall.service;

import com.shoppingMall.model.Product;
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

    public boolean saveProduct(MultipartFile file, Product product){

        boolean flag = false;

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        //Extract the image extension
        String ext = fileName.substring(fileName.indexOf(".")+1);

        //set image extension
        product.setImageExt(ext);

        if(fileName.contains("..")){
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

    public List<Product> getProducts(){
        List<Product> product = new ArrayList<>();

        try {
            product = productRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }

    public Product getProductById(Long productId){
        Product product = null;

        try {
            product = productRepository.findById(productId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }
}
