package estore.estore.dao;

import estore.estore.model.Account;
import estore.estore.model.Cart;
import estore.estore.model.Category;
import estore.estore.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CartDAO {

    private JdbcTemplate jdbcTemplate;

    public CartDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addProduct(int productId, int accountId, int stock){
        String sql = "insert into cart(productId, accountId, stock) value (?, ?, ?)";
        int result = jdbcTemplate.update(sql, productId, accountId, stock);
        return result;
    }

    public List<Product> getAll(int accountId){
        String sql = "select p.id, p.image, p.product_name, p.cost_price, c.stock from product p inner join cart c " +
                "on p.id = c.productId where c.accountId = ?";
        List<Product> productList = jdbcTemplate.query(sql, new Object[]{accountId}, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setImage(rs.getString("image"));
                product.setProductName(rs.getString("product_name"));
                product.setCostPrice(rs.getDouble("cost_price"));
                product.setStock(rs.getInt("stock"));

                return product;
            }
        });
        return productList;
    }

    public Cart getCart(int productId, int accountId){
        String sql = "select * from cart where productId = ? and accountId = ?";
        try{
            return jdbcTemplate.queryForObject(sql, new Object[]{productId, accountId}, (rs, rowNum) -> {
                Cart cart = new Cart();
                Product product = new Product();
                Account account = new Account();
                product.setId(rs.getInt("productId"));
                account.setId(rs.getInt("accountId"));
                cart.setProduct(product);
                cart.setAccount(account);
                cart.setStock(rs.getInt("stock"));

                return cart;
            });
        } catch (Exception e) {
            return null;
        }

    }

    public int updateCart(int productId, int accountId, int stock){
        String sql = "update cart set stock = ? where productId= ? and accountId = ?";
        int result = jdbcTemplate.update(sql, stock, productId, accountId);
        return result;
    }

    public int removeProduct(int productId, int accountId){
        String sql = "delete from cart where productId = ? and accountId = ?";
        int result = jdbcTemplate.update(sql, productId, accountId);
        return result;
    }
}
