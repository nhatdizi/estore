package estore.estore.controller.admin;

import estore.estore.dao.CategoryDAO;
import estore.estore.model.Account;
import estore.estore.model.Category;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryDAO categoryDAO;

    @GetMapping("/category-management")
    public String categoryManagement(Model model,
                                     HttpSession session){
        Account account = (Account) session.getAttribute("user");

        if(account == null || account.getRole().getId() != 1){
            return "error";
        }else{
            model.addAttribute("pageTitle", "Product Details");
            List<Category> categoryList = categoryDAO.getAll();
            model.addAttribute("categoryList", categoryList);
            return "CategoryManagement";
        }
    }

    @GetMapping("/add-category")
    public String addCategory(Model model,
                              HttpSession session){
        Account account = (Account) session.getAttribute("user");

        if(account == null || account.getRole().getId() != 1){
            return "error";
        }else {
            model.addAttribute("pageTitle", "Product Details");
            return "AddCategory";
        }
    }

    @GetMapping("/edit-category/{id}")
    public String editCategory(@PathVariable("id") int id, Model model,
                               HttpSession session){
        Account account = (Account) session.getAttribute("user");

        if(account == null || account.getRole().getId() != 1){
            return "error";
        }else {
            model.addAttribute("pageTitle", "Product Details");

            Category category = categoryDAO.getById(id);
            System.out.println("test category: " + category);
            model.addAttribute("category", category);
            return "EditCategory";
        }
    }

    @PostMapping("/category/add")
    public String addCategory(@RequestParam("category") String name,
                              HttpSession session){
        Account account = (Account) session.getAttribute("user");

        if(account == null || account.getRole().getId() != 1){
            return "error";
        }else {
            int result = categoryDAO.addCategory(name);
            return "redirect:/admin/category-management";
        }
    }

    @PostMapping("/category/edit/{id}")
    public String updateCategory(@PathVariable("id") int id,
            @RequestParam("name") String name,
             HttpSession session){
        Account account = (Account) session.getAttribute("user");

        if(account == null || account.getRole().getId() != 1){
            return "error";
        }else {
            int result = categoryDAO.updateCategory(id, name);
            return "redirect:/admin/category-management";
        }
    }

    @PostMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session){
        Account account = (Account) session.getAttribute("user");

        if(account == null || account.getRole().getId() != 1){
            return "error";
        }else {
            int result = categoryDAO.deleteCategory(id);
            if (result == 0) {
                // Thêm thông báo vào RedirectAttributes
                redirectAttributes.addFlashAttribute("msg", "Danh mục này đang là khoá ngoại của sản phẩm");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Danh mục đã được xóa thành công");
            }

            return "redirect:/admin/category-management";
        }
    }
}
