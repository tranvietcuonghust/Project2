package com.cuongtv.WebShop.Order;

import com.cuongtv.WebShop.Product.Product;
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
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;
    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;
    private Long quantity;
    private Double price;
}
