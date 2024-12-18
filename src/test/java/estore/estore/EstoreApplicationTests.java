package estore.estore;

import estore.estore.dao.AccountDao;
import estore.estore.dao.CartDAO;
import estore.estore.dao.CategoryDAO;
import estore.estore.model.Account;
import estore.estore.model.Category;
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
	void contextLoads() {
		CategoryDAO categoryDAO = new CategoryDAO(jdbcTemplate);

//		List<Category> categoryList = categoryDAO.getAll();
//		System.out.println(categoryList);

		Category category = categoryDAO.getById(1);
		System.out.println(category);
	}

}
