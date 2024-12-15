package estore.estore.dao;

import estore.estore.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FavoriteDAO {

    private JdbcTemplate jdbcTemplate;

    public FavoriteDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addProduct(int productId, int accountId){
        String sql = "insert into favorite(productId, accountId) value (?, ?)";
        int result = jdbcTemplate.update(sql, productId, accountId);
        return result;
    }

    public List<Product> getAll(int accountId){
//        String sql = "select p.image, p.product_name, p.cost_price from product p inner join cart c " +
//                "on p.id = c.productId where c.accountId = ?";
        String sql = "select p.id, p.image, p.product_name, p.cost_price, c.name from product p inner join favorite f " +
                        "on p.id = f.productId " +
                        "inner join category c on p.categoryId = c.id where f.accountId = ?";
        List<Product> productList = jdbcTemplate.query(sql, new Object[]{accountId}, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setImage(rs.getString("image"));
                product.setProductName(rs.getString("product_name"));
                product.setCostPrice(rs.getDouble("cost_price"));

                Category category = new Category();
                category.setName(rs.getString("name"));

                product.setCategory(category);
                return product;
            }
        });
        return productList;
    }

    public Favorite getFavorite(int productId, int accountId){
        String sql = "select * from favorite where productId = ? and accountId = ?";

        try{
            return jdbcTemplate.queryForObject(sql, new Object[]{productId, accountId}, (rs, rowNum) -> {
                Favorite favorite = new Favorite();
                Product product = new Product();
                Account account = new Account();
                product.setId(rs.getInt("productId"));
                account.setId(rs.getInt("accountId"));
                favorite.setProduct(product);
                favorite.setAccount(account);

                return favorite;
            });
        } catch (Exception e) {
            return null;
        }

    }

}
