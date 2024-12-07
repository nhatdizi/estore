package estore.estore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CartKey implements Serializable {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "account_id")
    private Long accountId;


}
