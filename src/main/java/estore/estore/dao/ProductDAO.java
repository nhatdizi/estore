package estore.estore.dao;

import estore.estore.model.Category;
import estore.estore.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public List<Product> sortByCostPrice(String type){
        String sql = "select * from product order by cost_price " + type;

        try {
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

    public int deleteProduct(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int addProduct(Product product, MultipartFile imageFile) {
        // Bước 1: Lưu ảnh vào thư mục (nếu có ảnh)
        String imageName = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageName = saveImage(imageFile);  // Hàm lưu ảnh vào thư mục
        }

        // Bước 2: Thêm sản phẩm vào cơ sở dữ liệu
        String sql = "INSERT INTO product (product_name, image, cost_price, original_price, stock, description, categoryId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Lấy id của category
        int categoryId = product.getCategory().getId();

        // Chạy câu lệnh insert
        return jdbcTemplate.update(sql, product.getProductName(), imageName, product.getCostPrice(),
                product.getOriginalPrice(), product.getStock(), product.getDescription(), categoryId);
    }

    // Hàm lưu ảnh vào thư mục
    public String saveImage(MultipartFile imageFile) {
        String fileName = imageFile.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/img/categori/" + fileName);

        // Đảm bảo thư mục tồn tại
        try {
            Files.createDirectories(path.getParent());  // Tạo thư mục nếu chưa tồn tại
            Files.write(path, imageFile.getBytes());   // Lưu tệp hình ảnh vào thư mục static/img/category
        } catch (IOException e) {
            throw new RuntimeException("Không thể lưu tệp hình ảnh: " + e.getMessage());
        }
        return fileName;
    }

    public int updateProduct(Product product) {
        String sql = "UPDATE product SET product_name = ?, image = ?, cost_price = ?, original_price = ?, stock = ?, description = ?, categoryId = ? WHERE id = ?";

        return jdbcTemplate.update(sql, product.getProductName(), product.getImage(),
                product.getCostPrice(), product.getOriginalPrice(), product.getStock(),
                product.getDescription(), product.getCategory().getId(), product.getId());
    }


}
