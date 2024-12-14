package estore.estore.dao;

import estore.estore.model.Category;
import estore.estore.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ProductDAO{

//    private JdbcTemplate jdbcTemplate;
    private JdbcTemplate jdbcTemplate;
    private CategoryDAO categoryDAO;

//    @Autowired
//    public ProductDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }


    public ProductDAO(JdbcTemplate jdbcTemplate, CategoryDAO categoryDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryDAO = categoryDAO;
    }

    public List<Product> getAll(){
        String sql = "select * from product";
        List<Product> productList = jdbcTemplate.query(sql, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setImage(rs.getString("image"));
                product.setProductName(rs.getString("product_name"));
                product.setCostPrice(rs.getDouble("cost_price"));
                product.setOriginalPrice(rs.getDouble("original_price"));
                product.setStock(rs.getInt("stock"));
                product.setDescription(rs.getString("description"));

                //các dữ liệu trong ô getString là tên cột trong mysql
                int categoryId = rs.getInt("categoryId");
//                CategoryDAO categoryDAO = new CategoryDAO(jdbcTemplate);
                Category category = categoryDAO.getById(categoryId);
//                category.setId(categoryId);
                product.setCategory(category);
                return product;
            }
        });
        return productList;
    }

    public Product getById(int id) {
        String sql = "select * from product where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setImage(rs.getString("image"));
            product.setProductName(rs.getString("product_name"));
            product.setCostPrice(rs.getDouble("cost_price"));
            product.setOriginalPrice(rs.getDouble("original_price"));
            product.setStock(rs.getInt("stock"));
            product.setDescription(rs.getString("description"));

            Category category = categoryDAO.getById(rs.getInt("categoryId"));
            product.setCategory(category);
            return product;
        });
    }

    public List<Product> getByName(String name){
        String sql = "select * from product where product_name like ?";
        name = "%" + name + "%";

        try {
            List<Product> productList = jdbcTemplate.query(sql, new Object[]{name}, new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setImage(rs.getString("image"));
                    product.setProductName(rs.getString("product_name"));
                    product.setCostPrice(rs.getDouble("cost_price"));
                    product.setOriginalPrice(rs.getDouble("original_price"));
                    product.setStock(rs.getInt("stock"));
                    product.setDescription(rs.getString("description"));

                    //các dữ liệu trong ô getString là tên cột trong mysql
                    int categoryId = rs.getInt("categoryId");
                    Category category = categoryDAO.getById(categoryId);
                    product.setCategory(category);
                    return product;
                }
            });
            return productList;
        } catch (Exception e) {
           return null;
        }
    }
}
