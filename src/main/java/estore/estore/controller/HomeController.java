package estore.estore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("pageTitle", "Home");
        return "index";
    }

    @GetMapping("/catagori")
    public String catagori(Model model){
        model.addAttribute("pageTitle", "Catagori");
        return "Catagori";
    }

    @GetMapping("/blog")
    public String blog(Model model){
        model.addAttribute("pageTitle", "Blog");
        return "blog";
    }

    @GetMapping("/single-blog")
    public String singleBlog(Model model){
        model.addAttribute("pageTitle", "Blog Details");
        return "single-blog";
    }

    @GetMapping("/contact")
    public String contact(Model model){
        model.addAttribute("pageTitle", "Contact");
        return "contact";
    }

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("pageTitle", "About");
        return "about";
    }

    @GetMapping("/element")
    public String element(Model model){
        model.addAttribute("pageTitle", "Element");
        return "elements";
    }

    @GetMapping("/confirmation")
    public String confirmation(Model model){
        model.addAttribute("pageTitle", "Confirmation");
        return "confirmation";
    }

    @GetMapping("/cart")
    public String cart(Model model){
        model.addAttribute("pageTitle", "Cart");
        return "cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("pageTitle", "Checkout");
        return "checkout";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("pageTitle", "Login");
        return "login";
    }

    @GetMapping("/product-list")
    public String productList(Model model){
        model.addAttribute("pageTitle", "Product List");
        return "product_list";
    }

    @GetMapping("/single-product")
    public String singleProduct(Model model){
        model.addAttribute("pageTitle", "Product Details");
        return "single-product";
    }
}
