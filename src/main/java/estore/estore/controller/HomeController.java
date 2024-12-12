package estore.estore.controller;

import estore.estore.dao.ProductDAO;
import estore.estore.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {

    private ProductDAO productDAO;

    public HomeController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("pageTitle", "Home");
        List<Product> productList = productDAO.getAll();
        System.out.println("productList: " + productList);
        model.addAttribute("productList", productList);
        return "index";
    }

    @GetMapping("/catagori")
    public String catagori(Model model){
        model.addAttribute("pageTitle", "Catagori");
        List<Product> productList = productDAO.getAll();
        System.out.println("productList: " + productList);
        model.addAttribute("productList", productList);
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
        List<Product> productList = productDAO.getAll();
        System.out.println("productList: " + productList);
        model.addAttribute("productList", productList);
        return "product_list";
    }

    @GetMapping("/single-product")
    public String singleProduct(Model model){
        model.addAttribute("pageTitle", "Product Details");
        Product product = productDAO.getById(1);
        model.addAttribute("product", product);
        return "single-product";
    }

    @GetMapping("/single-product/{id}")
    public String singleProductDetail(@PathVariable("id") int id, Model model){
        model.addAttribute("pageTitle", "Product Details");
        Product product = productDAO.getById(id);
        model.addAttribute("product", product);
        return "single-product";
    }
}
