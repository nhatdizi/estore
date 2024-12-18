package estore.estore.dao;

import estore.estore.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Component
public class CategoryDAO{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Category> getAll(){
        String sql = "select * from category";
        try{
            List<Category> categoryList = jdbcTemplate.query(sql, new RowMapper<Category>() {
                @Override
                public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Category category = new Category();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    return category;
                }
            });
            return categoryList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList(); // Trả về danh sách rỗng thay vì null
        }
    }

    public Category getById(int id) {
        String sql = "select * from category where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            return category;
        });
    }

    public int addCategory(String name){
        String sql = "insert into category(name) value (?)";
        return jdbcTemplate.update(sql, name);
    }

    public int updateCategory(int id, String name){
        String sql = "update category set name = ? where id = ?";
        return jdbcTemplate.update(sql, name, id);
    }

    public int deleteCategory(int id){
        String checkSql = "SELECT COUNT(*) FROM product WHERE categoryId = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, id);
        if(count > 0){
            return 0;
        }else{
            String sql = "delete from category where id = ?";
            return jdbcTemplate.update(sql, id);
        }
    }
}
