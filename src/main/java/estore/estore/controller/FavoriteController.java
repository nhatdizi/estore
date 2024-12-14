package estore.estore.controller;

import estore.estore.dao.CartDAO;
import estore.estore.dao.FavoriteDAO;
import estore.estore.model.Account;
import estore.estore.model.Cart;
import estore.estore.model.Favorite;
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

import java.util.List;

@Controller
public class FavoriteController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/add-to-favorite/{id}")
    public String addToCart(@PathVariable("id") int productId,
                            HttpSession session){
        Account account = (Account) session.getAttribute("user");

        if(account == null){
            return "redirect:/login";
        }
        FavoriteDAO favoriteDAO = new FavoriteDAO(jdbcTemplate);

        Favorite favorite = favoriteDAO.getFavorite(productId, account.getId());
        int result = 0;

        if(favorite == null){
            result = favoriteDAO.addProduct(productId, account.getId());
            return "redirect:/product-list";
        }
        return "redirect:/product-list";
    }

    @GetMapping("/product-list")
    public String cart(Model model, HttpSession session){
        FavoriteDAO favoriteDAO = new FavoriteDAO(jdbcTemplate);
        model.addAttribute("pageTitle", "Wish List");
        Account account = (Account) session.getAttribute("user");
        if (account == null){
            return "redirect:/login";
        }
        List<Product> productList = favoriteDAO.getAll(account.getId());

        model.addAttribute("productList", productList);
        return "product_list";
    }

}
