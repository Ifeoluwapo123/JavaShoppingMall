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

    /**
     * POST request to post products
     * redirects user if not in session
     * save product to database
     * */
    @RequestMapping(value = "/adminFormProcessing", method = RequestMethod.POST)
    public String processProductUpload(HttpServletRequest request, HttpServletResponse response,
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

    /**
     * GET request to display a particular product
     * redirects user if not in session
     * orders made on the product is also displayed
     * */
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

    /**
     * POST request to add product to cart
     * redirects user if not in session
     * save orders in the database, or perhaps fails
     * */
    @RequestMapping(value = "/add_to_cart/{id}", method = RequestMethod.POST)
    public @ResponseBody String addCart(@PathVariable("id") Long id, Model model, HttpSession session) {

        Product product = productService.getProductById(id);

        Person person = (Person) session.getAttribute("user");
        if (person == null) return "redirect:/";

        boolean success = orderService.addOrder(person, product);
        if(success){
            System.out.println("success");
            return "success";
        }else{
            System.out.println("Not successful");
            return "failed";
        }
    }

    /**
     * GET request to show edit page
     * redirects user if not in session
     * */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model, HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if (person == null) return "redirect:/";

        Product product = productService.getProductById(id);

        model.addAttribute("products", product);
        return "edit";
    }

    /**
     * GET request to edit product
     * redirects user if not in session
     * edit product in the database
     * or perhaps fails
     * */
    @RequestMapping(value = "/editPostProcessing", method = RequestMethod.POST)
    public String edit(@ModelAttribute("product") Product product, HttpSession session, @RequestParam("file") MultipartFile imageFile) {

        Person person = (Person) session.getAttribute("user");
        if (person == null) return "redirect:/";

        if(productService.editProduct(imageFile, product)){
            session.setAttribute("message", "Successfully edited Posted!!!");
        }else{
            session.setAttribute("message", "failed to edit Posted!!!");
        }

        return "redirect:/home";
    }

    /**
     * GET request to delete product
     * redirects user if not in session
     * delete product in the database
     * or perhaps fails
     * */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, HttpSession session) {

        Person person = (Person) session.getAttribute("user");
        if (person == null) return "redirect:/";

        if( productService.deleteProduct(id)){
            session.setAttribute("message", "Successfully deleted Product!!!");
        }else{
            session.setAttribute("message", "failed to delete Product!!!");
        }

        return "redirect:/home";
    }

}