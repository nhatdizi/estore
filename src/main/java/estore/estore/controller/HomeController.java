package estore.estore.controller;

import estore.estore.dao.CartDAO;
import estore.estore.dao.ProductDAO;
import estore.estore.model.Account;
import estore.estore.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
//        System.out.println("productList: " + productList);
        System.out.println("load thành công");
        model.addAttribute("productList", productList);
        return "index";
    }

    @GetMapping("/catagori")
    public String catagori(Model model){
        model.addAttribute("pageTitle", "Catagori");
        List<Product> productList = productDAO.getAll();
        model.addAttribute("productList", productList);
        return "Catagori";
    }

    @GetMapping("/categori/sort")
    public String sortProduct(@RequestParam("select") String sortProduct,
                              Model model){
        model.addAttribute("pageTitle", "Catagori");

        List<Product> productList = productDAO.sortByCostPrice(sortProduct);
        System.out.println("sortProduct: " + sortProduct);
        System.out.println("productList: " + productList);
        model.addAttribute("productList", productList);
        return "Catagori";
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

    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("pageTitle", "Checkout");
        return "checkout";
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

    @GetMapping("/search")
    public String search(@RequestParam("Search") String search, Model model){
        model.addAttribute("pageTitle", "Catagori");
        List<Product> productList = productDAO.getByName(search);
        System.out.println("productList: " + productList);
        model.addAttribute("productList", productList);
        return "Catagori";
    }

}
