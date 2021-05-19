package com.shoppingMall.controller;

import com.shoppingMall.model.Order;
import com.shoppingMall.model.Person;
import com.shoppingMall.model.Product;
import com.shoppingMall.service.OrderService;
import com.shoppingMall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/adminFormProcessing", method = RequestMethod.POST)
    public String processLogin(HttpServletRequest request, HttpServletResponse response,
                               @ModelAttribute("product") Product product, @RequestParam("file") MultipartFile imageFile, HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if (person == null) return "redirect:/";

        //set person who made the product which is the admin
        product.setPerson(person);

        if (productService.saveProduct(imageFile, product)) {
            session.setAttribute("message", "Successfully Posted!!!");
        } else {
            session.setAttribute("message", "Failed to Post!!!");
        }

        return "redirect:/home";

    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String showProductInfo(@PathVariable("id") Long id, Model model, HttpSession session) {

        Product product = productService.getProductById(id);

        Person person = (Person) session.getAttribute("user");

        Order order = orderService.getOrder(person, product);
        Long numberOfOrders = 0L;

        if(order != null){
            numberOfOrders = order.getMyOrder();
        }

        if (person == null) return "redirect:/";

        model.addAttribute("product", product);
        model.addAttribute("orders", numberOfOrders);

        return "product-details";
    }

    //add_to_cart
    @RequestMapping(value = "/add_to_cart/{id}", method = RequestMethod.GET)
    public String addToCart(@PathVariable("id") Long id, Model model, HttpSession session) {

        Product product = productService.getProductById(id);

        Person person = (Person) session.getAttribute("user");

        boolean success = orderService.addOrder(person, product);
        if(success){
            System.out.println("success");
        }else{
            System.out.println("Not successful");
        }

        if (person == null) return "redirect:/";

        Order order = orderService.getOrder(person, product);
        Long numberOfOrders = 0L;

        if(order != null){
            numberOfOrders = order.getMyOrder();
        }

        model.addAttribute("product", product);
        model.addAttribute("orders", numberOfOrders);

        return "product-details";
    }

}