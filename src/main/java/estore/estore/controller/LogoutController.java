package estore.estore.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Hủy session khi người dùng đăng xuất
        System.out.println("logout thành công");
        return "redirect:/"; // Quay lại trang chủ
    }
}
