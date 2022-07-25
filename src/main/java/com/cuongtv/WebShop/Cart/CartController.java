package com.cuongtv.WebShop.Cart;

import com.cuongtv.WebShop.Customer.Customer;
import com.cuongtv.WebShop.Customer.CustomerService;
import com.cuongtv.WebShop.Order.Order;
import com.cuongtv.WebShop.Order.OrderDTO;
import com.cuongtv.WebShop.Product.Product;
import com.cuongtv.WebShop.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    ProductService productService;
    @Autowired
    CartService cartService;
    @Autowired
    CustomerService customerService;
    @GetMapping("/cart")
    public String cartGet(Model model){
        Customer customer = customerService.getCurrentCustomer();
        List<Product> productList = cartService.listCartItems(customer);
        model.addAttribute("cartCount", productList.size());
        model.addAttribute("total", productList.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", productList);
        return "cartCopy";
    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable long id){
        Product product = productService.getProductById(id).get();
        Customer customer = customerService.getCurrentCustomer();
        cartService.addToCart(customer, product);
        return "redirect:/shop";
    }//click add from page viewProduct

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index){
        Customer customer = customerService.getCurrentCustomer();
        cartService.removeFromCart(customer, index);
        return "redirect:/cart";
    } // delete 1 product

    @GetMapping("/checkout")
    public String checkout(Model model){
        Customer customer = customerService.getCurrentCustomer();
        List<Product> productList = cartService.listCartItems(customer);
        model.addAttribute("cartCount", productList.size());
        model.addAttribute("total", productList.stream().mapToDouble(Product::getPrice).sum());
        //model.addAttribute("cart", GlobalData.cart);
        model.addAttribute("orderDTO", new OrderDTO());
        return "checkoutCopy";
    } // checkout totalPrice
}
