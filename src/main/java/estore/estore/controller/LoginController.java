package estore.estore.controller;

import estore.estore.dao.AccountDao;
import estore.estore.model.Account;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
public class LoginController {
    private AccountDao accountDao;

    public LoginController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("pageTitle", "Login");
        return "login"; // Trả về trang login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
                        Model model) {
        // Lấy thông tin tài khoản từ cơ sở dữ liệu
        Account account = accountDao.getByName(username);

        System.out.println("account: " + account);
        // Kiểm tra mật khẩu
        if (account != null && account.getPassword().equals(password)) {
            // Nếu đăng nhập thành công, lưu thông tin người dùng vào session
            session.setAttribute("user", account);
            // Điều hướng đến trang chủ hoặc trang khác tùy theo vai trò người dùng
//            if (account.getRole().getPosition().equals("admin")) {
//                return "redirect:/admin/category-management"; // Trang dành cho quản trị viên
//            } else {
//                model.addAttribute("pageTitle", "Home");
//                return "redirect:/"; // Trang dành cho người dùng bình thường
//            }
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid username or password");
            model.addAttribute("pageTitle", "Login");
            return "login"; // Nếu đăng nhập thất bại, quay lại trang login
        }
    }

}
