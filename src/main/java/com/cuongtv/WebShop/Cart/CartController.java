package com.cuongtv.WebShop.Cart;

import com.cuongtv.WebShop.Customer.Customer;
import com.cuongtv.WebShop.Customer.CustomerService;
import com.cuongtv.WebShop.Order.Order;
import com.cuongtv.WebShop.Order.OrderDTO;
import com.cuongtv.WebShop.Product.Product;
import com.cuongtv.WebShop.Product.ProductService;
import com.cuongtv.WebShop.Product.ProductVar;
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
        List<ProductVar> productList = cartService.listCartItems(customer);
        Double total = 0.0;
        for(ProductVar productVar : productList){
            total+=productVar.getProduct().getPrice();
        }
        model.addAttribute("cartCount", productList.size());
        model.addAttribute("total", total);
        model.addAttribute("cart", productList);
        return "cartCopy";
    }

    @GetMapping("/addToCart/{sizeid}")
    public String addToCart(@PathVariable long sizeid){
       //Product product = productService.getProductById(id).get();
        ProductVar productVar = productService.getProductVarById(sizeid).get();
        Customer customer = customerService.getCurrentCustomer();
        cartService.addToCart(customer, productVar);
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
        List<ProductVar> productList = cartService.listCartItems(customer);
        double total=0.0;
        for(ProductVar productVar : productList){
            total+=productVar.getProduct().getPrice();
        }
        model.addAttribute("cartCount", productList.size());
        model.addAttribute("total", total);
        //model.addAttribute("cart", GlobalData.cart);
        model.addAttribute("orderDTO", new OrderDTO());
        return "checkoutCopy";
    } // checkout totalPrice
}
