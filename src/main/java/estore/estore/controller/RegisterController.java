package estore.estore.controller;

import estore.estore.dao.AccountDao;
import estore.estore.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("pageTitle", "Register");
        return "register";
    }

    @PostMapping("/register")
    public String addAccount(@RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("re-enter-password") String reEnterPassword,
                             Model model){

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setEmail(email);

        AccountDao accountDao = new AccountDao(jdbcTemplate);
        String error = "";
        int result = accountDao.add(account);
        if(result == 0){
            error = "Username đã tồn tại!";
            model.addAttribute("error", error);
            model.addAttribute("pageTitle", "Register");
            return "/register";
        } else {
            if(password.equals(reEnterPassword) == false){
                error = "Mật khẩu nhập lại không khớp";
                model.addAttribute("error", error);
                model.addAttribute("pageTitle", "Register");
                return "/register";
            }
            return "redirect:/login";
        }
    }
}
