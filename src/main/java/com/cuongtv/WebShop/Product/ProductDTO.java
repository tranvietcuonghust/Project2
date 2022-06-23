package com.cuongtv.WebShop.Product;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String imageName;
}
