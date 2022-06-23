package com.cuongtv.WebShop.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    public  ProductRepository productRepository;
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }


    public void addProduct(Product product) {
        productRepository.save(product);
    }
    public void removeProduct(Long Id)
    {
        Product product = productRepository.findById(Id).get();
        productRepository.delete(product);
    }



    public Optional<Product> getProductById(Long Id)
    {
        return productRepository.findById(Id);
    }
}
