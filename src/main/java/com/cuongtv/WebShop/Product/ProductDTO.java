package com.cuongtv.WebShop.Product;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String imageName;
    private List<ProductVar> vars = new ArrayList<>();
}
