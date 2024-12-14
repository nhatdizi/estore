package estore.estore;

import estore.estore.dao.AccountDao;
import estore.estore.dao.CartDAO;
import estore.estore.model.Account;
import estore.estore.model.Product;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootTest
class EstoreApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads(HttpSession session) {
		CartDAO cartDAO = new CartDAO(jdbcTemplate);
		Account account = (Account) session.getAttribute("user");
		List<Product> productList = cartDAO.getAll(account.getId());
		System.out.println(productList);
	}

}
