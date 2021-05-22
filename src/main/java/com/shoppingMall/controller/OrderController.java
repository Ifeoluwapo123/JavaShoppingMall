package com.shoppingMall.controller;

import com.shoppingMall.model.Person;
import com.shoppingMall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Delete request on orders made by users
     * redirects user if not in session
     * delete orders in the database, or perhaps fails
     * redirect back to home page
     * */
    @RequestMapping(value = "/deleteOrder/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, HttpSession session) {

        Person person = (Person) session.getAttribute("user");
        if (person == null) return "redirect:/";

        if( orderService.deleteOrder(id)){
            session.setAttribute("message", "Successfully deleted Order!!!");
        }else{
            session.setAttribute("message", "failed to delete Order!!!");
        }

        return "redirect:/home";
    }
}
