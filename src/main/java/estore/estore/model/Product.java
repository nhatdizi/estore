package estore.estore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id;
    private String image;
    private String productName;
    private double costPrice;
    private double originalPrice;
    private int stock;
    private String description;
    private Category category;
}
