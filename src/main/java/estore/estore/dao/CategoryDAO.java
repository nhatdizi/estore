package estore.estore.dao;

import estore.estore.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDAO implements BaseDAO<Category>{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> getAll() {
        return List.of();
    }

    @Override
    public Category getById(int id) {
        String sql = "select * from category where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            return category;
        });
    }

    @Override
    public Category getByName(String name) {
        return null;
    }

    @Override
    public int add(Category category) {
        return 0;
    }

}
