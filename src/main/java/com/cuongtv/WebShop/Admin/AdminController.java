package com.cuongtv.WebShop.Admin;

import com.cuongtv.WebShop.Product.Product;
import com.cuongtv.WebShop.Product.ProductDTO;
import com.cuongtv.WebShop.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {

    public static String uploadDir= System.getProperty("user.dir")+ "/src/main/resources/static/productImages";
    @Autowired
    ProductService productService;

    @GetMapping("/admin")
    public String adminHome()
    {
        return("adminhomecopy");
    }

    @GetMapping("/admin/products")
    public String ProductManage(Model model)
    {
        model.addAttribute("products",productService.getAllProduct());
        return "products";
    }
    @GetMapping("/admin/products/add")
    public String getProductAdd(Model model)
    {
        model.addAttribute("productDTO", new ProductDTO());
        return "product_add";
    }
    @PostMapping("/admin/products/add")
    public String postProductAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
                                 @RequestParam("productImage") MultipartFile file,
                                 @RequestParam("imageName") String imageName) throws IOException{
        //product set....,DTO = request, giong service
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir,imageUUID);
            Files.write(fileNameAndPath,file.getBytes());
        }
        else{
            imageUUID=imageName;
        }
        Product product =new Product();
        product.setProduct_id(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImageName(imageUUID);
        productService.addProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/delete/{id}")
    public String getProductDelete(@PathVariable Long id)
    {
        productService.removeProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/update/{id}")
    public String getProductUpdate(@PathVariable Long id, Model model)
    {
        Product product = productService.getProductById(id).get();
        //ProductDTO
        // product.set.....
        //model.addAttribute();
        return "product_update";
    }











}
