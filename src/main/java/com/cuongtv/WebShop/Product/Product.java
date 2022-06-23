package com.cuongtv.WebShop.Product;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @Column (name = "product_size")
    private double size;
    @Column (name = "quantity")
    private int quantity;
    @Lob
    @Column (name = "description")
    private String description;
    @Column (name = "image")
    private String imageName;
}
