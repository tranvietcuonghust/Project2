package com.cuongtv.WebShop.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static com.cuongtv.WebShop.Admin.AdminController.uploadDir;
import static com.cuongtv.WebShop.Admin.AdminController.uploadDir2;

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
    public void updateProduct(Long Id, Product product, MultipartFile file) throws IOException {
        Product oldProduct = productRepository.findById(Id).get();
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir,imageUUID);
            Files.write(fileNameAndPath,file.getBytes());
            Path fileNameAndPath2 = Paths.get(uploadDir2,imageUUID);
            Files.write(fileNameAndPath2,file.getBytes());
        }
        else{
            imageUUID=product.getImageName();
        }
        if (file.isEmpty()) {
            product.setImageName(oldProduct.getImageName());
        } else {

            product.setImageName(imageUUID);
        }
        productRepository.save(product);
    }

    @Transactional
    public List<Product> listAll(String keyword) {
        if (keyword != null) {
            return productRepository.search(keyword);
        }
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long Id)
    {
        return productRepository.findById(Id);
    }
}
