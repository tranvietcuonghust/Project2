package com.cuongtv.WebShop.Product;


import com.cuongtv.WebShop.Cart.Cart;
import com.cuongtv.WebShop.Order.OrderedItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table (name ="products")
@Getter
@Setter

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long product_id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "price")
    private double price;
    @Lob
    @Column (name = "description")
    private String description;
    @Column (name = "image")
    private String imageName;


    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductVar> productVars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(product_id, product.product_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id);
    }
}
