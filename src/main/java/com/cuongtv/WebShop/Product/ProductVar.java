package com.cuongtv.WebShop.Product;

import com.cuongtv.WebShop.Cart.Cart;
import com.cuongtv.WebShop.Order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name ="productvars")
@Getter
@Setter
public class ProductVar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productvar_id")
    private Long id;
    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;
    @Column (name = "product_size")
    private String size;
    @ManyToMany(mappedBy = "productvar")
    private List<Cart> cart;
    @Column (name = "stock")
    private int stock;
}
