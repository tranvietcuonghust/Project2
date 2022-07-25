package com.cuongtv.WebShop.Order;

import com.cuongtv.WebShop.Cart.CartDTO;
import com.cuongtv.WebShop.Cart.CartService;
import com.cuongtv.WebShop.Customer.Customer;
import com.cuongtv.WebShop.Customer.CustomerService;
import com.cuongtv.WebShop.Product.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CartService cartService;

    @PostMapping("/payNow")
    public String placeOrder(@ModelAttribute("orderDTO") OrderDTO orderDTO) {

        orderService.placeOrder(orderDTO, customerService.getCurrentCustomer());

        return "shopcopy";
    }

//    @GetMapping("/add")
//    public String getAllOrders(@RequestParam("token") String token)
//            //throws AuthenticationFailException
//    {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String currentUsername = ((UserDetails)principal).getUsername();
//        Customer customer = (Customer) customerService.loadUserByUsername(currentUsername);
//        Long customerID = customer.getId();
//        List<Order> orderDtoList = orderService.listOrders(customerID);
//        return "Order-added";
//                //new ResponseEntity<List<Order>>(orderDtoList,HttpStatus.OK);
//    }
}
