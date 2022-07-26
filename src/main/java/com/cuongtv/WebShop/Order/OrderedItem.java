package com.cuongtv.WebShop.Order;

import com.cuongtv.WebShop.Product.Product;
import com.cuongtv.WebShop.Product.ProductVar;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name ="ordered_items")
@Getter
@Setter
public class OrderedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OrderedItemID;

    @OneToOne
    @JoinColumn(name = "productvar_id", referencedColumnName = "productvar_id")
    private ProductVar productvar;
    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;
    private Long quantity;
    private Double price;
}
