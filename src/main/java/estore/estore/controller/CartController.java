package estore.estore.controller;

import estore.estore.dao.CartDAO;
import estore.estore.model.Account;
import estore.estore.model.Cart;
import estore.estore.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable("id") int productId,
                            @RequestParam("stock") int stock,
                            HttpSession session){
        Account account = (Account) session.getAttribute("user");

        if(account == null){
            return "redirect:/login";
        }
        CartDAO cartDAO = new CartDAO(jdbcTemplate);

        Cart cart = cartDAO.getCart(productId, account.getId());
        int result = 0;

        if(cart == null){
            result = cartDAO.addProduct(productId, account.getId(), stock);
        }else{
            int stock2 = stock + cart.getStock();
            result = cartDAO.updateCart(productId, account.getId(), stock2);
        }
        return "redirect:/cart";
    }


    @GetMapping("/cart")
    public String cart(Model model, HttpSession session){
        CartDAO cartDAO = new CartDAO(jdbcTemplate);
        model.addAttribute("pageTitle", "Cart");
        Account account = (Account) session.getAttribute("user");
        if (account == null){
            return "redirect:/login";
        }
        List<Product> productList = cartDAO.getAll(account.getId());
        double subtotal = 0.0;
        for(Product product : productList){
            subtotal += product.getCostPrice()*product.getStock();
        }

        model.addAttribute("productList", productList);
        model.addAttribute("subtotal", subtotal);
        return "cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeProduct(@PathVariable("id") int productId,
                            HttpSession session){
        Account account = (Account) session.getAttribute("user");

        if(account == null){
            return "redirect:/login";
        }
        CartDAO cartDAO = new CartDAO(jdbcTemplate);

        int result =  cartDAO.removeProduct(productId, account.getId());

        return "redirect:/cart";
    }
}
