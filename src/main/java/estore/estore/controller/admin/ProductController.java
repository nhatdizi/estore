package estore.estore.controller.admin;

import estore.estore.dao.CategoryDAO;
import estore.estore.dao.ProductDAO;
import estore.estore.model.Account;
import estore.estore.model.Category;
import estore.estore.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @GetMapping("/product-management")
    public String productManagement(Model model,
                                    HttpSession session){
        Account account = (Account) session.getAttribute("user");

        if(account == null || account.getRole().getId() != 1){
            return "error";
        }else {
            model.addAttribute("pageTitle", "Product Details");
            List<Product> productList = productDAO.getAll();
            model.addAttribute("productList", productList);
            return "ProductManagement";
        }
    }

    @GetMapping("/add-product")
    public String addProductManagement(Model model, HttpSession session){
        Account account = (Account) session.getAttribute("user");

        if(account == null || account.getRole().getId() != 1){
            return "error";
        }else {
            model.addAttribute("pageTitle", "Product Details");

            List<Category> categoryList = categoryDAO.getAll();
            model.addAttribute("categoryList", categoryList);
            return "AddProduct";
        }
    }

    @GetMapping("/edit-product/{id}")
    public String editProductManagement(@PathVariable("id") int id,
                                Model model, HttpSession session){
        Account account = (Account) session.getAttribute("user");

        if(account == null || account.getRole().getId() != 1){
            return "error";
        }else {
            model.addAttribute("pageTitle", "Product Details");

            List<Category> categoryList = categoryDAO.getAll();
            model.addAttribute("categoryList", categoryList);

            Product product = productDAO.getById(id);
            model.addAttribute("product", product);
            return "EditProduct";
        }
    }

    @PostMapping("/product/add")
    public String addProduct(@RequestParam("name") String name,
                             @RequestParam("image") MultipartFile imageFile,
                             @RequestParam("costPrice") double costPrice,
                             @RequestParam("originalPrice") double originalPrice,
                             @RequestParam("stock") int stock,
                             @RequestParam("description") String description,
                             @RequestParam("category") int categoryId,
                             Model model, HttpSession session){

        Account account = (Account) session.getAttribute("user");

        // Kiểm tra quyền của người dùng
        if(account == null || account.getRole().getId() != 1){
            return "error"; // Hiển thị lỗi nếu không phải admin
        } else {
            // Lấy danh mục từ database
            Category category = categoryDAO.getById(categoryId);

            if (category == null) {
                model.addAttribute("msg", "Category not found.");
                return "redirect:/admin/product-management";
            }

            // Tạo sản phẩm mới
            Product product = new Product();
            product.setProductName(name);
            product.setCostPrice(costPrice);
            product.setOriginalPrice(originalPrice);
            product.setStock(stock);
            product.setDescription(description);
            product.setCategory(category);

            // Lưu sản phẩm vào cơ sở dữ liệu
            int result = productDAO.addProduct(product, imageFile);

            if (result > 0) {
                model.addAttribute("msg", "Product added successfully.");
            } else {
                model.addAttribute("msg", "Error occurred while adding product.");
            }

            return "redirect:/admin/product-management"; // Điều hướng về trang quản lý sản phẩm
        }
    }

    @PostMapping("/product/edit/{id}")
    public String updateProduct(@PathVariable("id") int id,
                                @RequestParam("name") String name,
                                @RequestParam("image") MultipartFile imageFile,
                                @RequestParam("costPrice") double costPrice,
                                @RequestParam("originalPrice") double originalPrice,
                                @RequestParam("stock") int stock,
                                @RequestParam("description") String description,
                                @RequestParam("category") int categoryId,
                                Model model, HttpSession session) {

        Account account = (Account) session.getAttribute("user");

        // Kiểm tra quyền của người dùng
        if (account == null || account.getRole().getId() != 1) {
            return "error"; // Hiển thị lỗi nếu không phải admin
        }

        // Lấy danh mục từ database
        Category category = categoryDAO.getById(categoryId);
        if (category == null) {
            model.addAttribute("msg", "Category not found.");
            return "redirect:/admin/product-management";
        }

        // Lấy sản phẩm cũ từ database
        Product product = productDAO.getById(id);
        if (product == null) {
            model.addAttribute("msg", "Product not found.");
            return "redirect:/admin/product-management";
        }

        // Cập nhật thông tin sản phẩm
        product.setProductName(name);
        product.setCostPrice(costPrice);
        product.setOriginalPrice(originalPrice);
        product.setStock(stock);
        product.setDescription(description);
        product.setCategory(category);

        // Nếu có ảnh mới, lưu ảnh mới
        if (!imageFile.isEmpty()) {
            String imageName = productDAO.saveImage(imageFile);// Lưu ảnh mới
            product.setImage(imageName); // Cập nhật tên ảnh
        }

        // Cập nhật sản phẩm vào cơ sở dữ liệu
        int result = productDAO.updateProduct(product);

        if (result > 0) {
            model.addAttribute("msg", "Product updated successfully.");
        } else {
            model.addAttribute("msg", "Error occurred while updating product.");
        }

        return "redirect:/admin/product-management"; // Điều hướng về trang quản lý sản phẩm
    }


    @PostMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable("id") int id, Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("user");

        if(account == null || account.getRole().getId() != 1) {
            return "error";
        }

        // Xóa sản phẩm theo id
        productDAO.deleteProduct(id);

//        model.addAttribute("msg", "Product deleted successfully");
        return "redirect:/admin/product-management";
    }

}
