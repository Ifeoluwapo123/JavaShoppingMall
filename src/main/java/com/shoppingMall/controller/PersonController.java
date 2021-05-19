package com.shoppingMall.controller;

import com.shoppingMall.model.Person;
import com.shoppingMall.model.Product;
import com.shoppingMall.service.PersonService;
import com.shoppingMall.service.ProductService;
import com.shoppingMall.utilities.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showRegister(HttpServletRequest request, HttpServletResponse response,
                               Model model, HttpSession session, @ModelAttribute("user") Person user) {

        if(user != null)
            model.addAttribute("person", user);
        else
            model.addAttribute("person", new Person());

        return "signup";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String showHome(HttpServletRequest request, HttpServletResponse response,
                               Model model, HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        Product product = new Product();

        model.addAttribute("product", product);

        //products in store
        List<Product> products = productService.getProducts();

        model.addAttribute("products", products);
        model.addAttribute("user", person);

        return "home";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("user", new Person());
        return "index";
    }

    @RequestMapping(value = "/loginProcessing", method = RequestMethod.POST)
    public String processLogin(HttpServletRequest request, HttpServletResponse response,
        @ModelAttribute("user") Person person, HttpSession session) {

        Person user = personService.loginUser(person.getEmail(), person.getPassword());

        if(user != null){
            session.setAttribute("user", user);
            session.removeAttribute("message");
            return "redirect:/home";
        }else {
            session.setAttribute("message", "Email or Password is wrong!!!");
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/signupProcessing", method = RequestMethod.POST)
    public String processRegistration(HttpServletRequest request, HttpServletResponse response,
                               @ModelAttribute("person") Person user, RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();

        //validation
        String validated = Validation.validateRegistration(user.getEmail(), user.getGender(), user.getPassword(), user.getPhoneNumber(),
                user.getFullName());

        if(!validated.equals("Successful validation")){
            session.setAttribute("message", validated);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/signup";
        }

        if(personService.createUser(user)){
            session.setAttribute("message", "Successfully Registered!!!");
        }else {
            session.setAttribute("message", "Registration failed!!! or Email already exists");
        }

        return "redirect:/signup";
    }
}