package com.cuongtv.WebShop.Admin;

import com.cuongtv.WebShop.Customer.Customer;
import com.cuongtv.WebShop.Customer.CustomerService;
import com.cuongtv.WebShop.Order.Order;
import com.cuongtv.WebShop.Order.OrderService;
import com.cuongtv.WebShop.Order.OrderedItem;
import com.cuongtv.WebShop.Product.Product;
import com.cuongtv.WebShop.Product.ProductDTO;
import com.cuongtv.WebShop.Product.ProductService;
import com.cuongtv.WebShop.Product.ProductVar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    public static String uploadDir= System.getProperty("user.dir")+ "/src/main/resources/static/productImages";
    public static String uploadDir2= System.getProperty("user.dir")+ "/target/classes/static/productImages";

    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;

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
    @GetMapping("/admin/customers")
    public String CustomerManage(Model model)
    {
        model.addAttribute("customers",customerService.getAllCustomer());
        return "customers";
    }
    @GetMapping("/admin/orders")
    public String OrderManage(Model model)
    {
        model.addAttribute("orders",orderService.getAllOrder());
        return "AdminOrderManage";
    }
    @GetMapping("/admin/orders/update/{id}")
    public String getOrderEdit(@PathVariable Long id, Model model)
    {
        Order order = orderService.getOrderById(id).get();
        List<OrderedItem> orderedItemList = orderService.getOrderedItem(id);
        model.addAttribute("order",order);
        model.addAttribute("orderitems",orderedItemList);
        return "viewOrder";
    }
    @PostMapping("/admin/orders/update/{id}")
    public String postOrderEdit(@ModelAttribute Order order)  {
        orderService.updateOrder(order);
        return "redirect:/admin/orders";
    }
    @GetMapping("/admin/products/add")
    public String getProductAdd(Model model)
    {
        model.addAttribute("productDTO", new ProductDTO());
        return "product_add";
    }


    @PostMapping("/admin/products/vars")
    public String setSizeStock(@RequestParam("size") String size,
                               @RequestParam("stock") int stock,
                               @ModelAttribute ProductDTO productDTO, Model model){
        ProductVar productVar = new ProductVar();
        productVar.setSize(size);
        productVar.setStock(stock);
        productDTO.getVars().add(productVar);
        model.addAttribute("productDTO", productDTO);
        return "product_add::#items";
    }

    @PostMapping("/admin/products/vars/delete/{index}")
    public String setSizeStock(@PathVariable int index,
                               @ModelAttribute ProductDTO productDTO, Model model){
        productDTO.getVars().remove(index);
        model.addAttribute("productDTO", productDTO);
        return "product_add::#items";
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
            Path fileNameAndPath2 = Paths.get(uploadDir2,imageUUID);
            Files.write(fileNameAndPath2,file.getBytes());
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
        for (ProductVar productVar : productDTO.getVars())
        {
            productVar.setProduct(product);
            productService.addProductVar(productVar);
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/delete/{id}")
    public String getProductDelete(@PathVariable Long id)
    {
        productService.removeProduct(id);
        return "redirect:/admin/products";
    }
    @PostMapping("/admin/products/update/vars")
    public String updateSizeStock(@RequestParam("size") String size,
                               @RequestParam("stock") int stock,
                               @ModelAttribute ProductDTO productDTO, Model model){
        ProductVar productVar = new ProductVar();
        productVar.setSize(size);
        productVar.setStock(stock);
//        GlobalData.productVars.add(productVar);
//        productDTO.setVars(GlobalData.productVars);
        model.addAttribute("productDTO", productDTO);
        return "product_update::#items";
    }

    @PostMapping("/admin/product/update/vars/delete/{index}")
    public String updateSizeStock(@PathVariable int index,
                               @ModelAttribute ProductDTO productDTO, Model model){
//        GlobalData.productVars.remove(index);
//        productDTO.setVars(GlobalData.productVars);
        model.addAttribute("productDTO", productDTO);
        return "product_update::#items";
    }

    @GetMapping("/admin/products/update/{id}")
    public String getProductUpdate(@PathVariable Long id, Model model)
    {
        Product product = productService.getProductById(id).get();
        model.addAttribute("product", product);
        return "product_update";
    }
    @PostMapping("/shop/product/update/{id}")
    public String processProductUpdate(@PathVariable Long id,
                                       @ModelAttribute Product product,
                                       @RequestParam("productImage") MultipartFile fileProductImage
                                       ) throws IOException {
        productService.updateProduct(id, product, fileProductImage);
        return "redirect:/admin/products";
    }
    @RequestMapping("/admin/orders/search")
    public String searchProducts(Model model,@RequestParam("id") String id,
                                 @RequestParam("name") String name,@RequestParam("phone") String phone,@RequestParam("status") String status){
        List<Order> orderList = orderService.multiSearch(id, name, phone, status);
        model.addAttribute("orders", orderList);
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("phone", phone);
        model.addAttribute("status", status);
        return "AdminOrderManage";
    }











}
