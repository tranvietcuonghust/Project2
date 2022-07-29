package com.cuongtv.WebShop;


import com.cuongtv.WebShop.Accounts.Account;
import com.cuongtv.WebShop.Customer.Customer;
import com.cuongtv.WebShop.Customer.CustomerDTO;
import com.cuongtv.WebShop.Customer.CustomerService;
import com.cuongtv.WebShop.Order.Order;
import com.cuongtv.WebShop.Order.OrderPDFExporter;
import com.cuongtv.WebShop.Order.OrderService;
import com.cuongtv.WebShop.Order.OrderedItem;
import com.cuongtv.WebShop.Product.Product;
import com.cuongtv.WebShop.Product.ProductService;
import com.cuongtv.WebShop.Product.ProductVar;
import com.cuongtv.WebShop.registration.RegistrationRequest;
import com.cuongtv.WebShop.registration.RegistrationService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private  ProductService productService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @GetMapping({"/","/home"} )
    public String viewHomePage(Model model) {
        model.addAttribute("products",productService.getAllProduct());
        Authentication a = SecurityContextHolder.getContext().getAuthentication();


        boolean b =  a instanceof AnonymousAuthenticationToken;
        if(!b){
            String email = a.getName();
            model.addAttribute("customer", customerService.loadUserByUsername(email));
        }
        model.addAttribute("not_authenticated", a instanceof AnonymousAuthenticationToken);
        return "home";
    }

    @GetMapping("/shop/product/{id}")
    public String viewProduct(Model model, @PathVariable Long id){
        model.addAttribute("product", productService.getProductById(id));
        return "prpductcopy";
    }


    @GetMapping("/register")
    public String signUp(Model model){
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "registercopy";
    }
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    public String register(RegistrationRequest request){
        registrationService.register(request);
        return "register_success";
    }

    @GetMapping("/login")
    public String logIn(Model model){
        return "logincopy";
    }

    @GetMapping("/loginAdmin")
    public String adminLogIn(Model model) {
        return "admin_login";
    }

    @GetMapping("/login_success")
    public String loginSuccess ()

    {
        return "login sucess";
    }


    @GetMapping("/shop")
    public String shop(Model model){
        //model.addAttribute("cartCount", GlobalData.cart.size());
        //model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProduct());
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable long id, Model model){
        //model.addAttribute("cartCount", GlobalData.cart.size());

        model.addAttribute("product", productService.getProductById(id).get());
        model.addAttribute("productvars", productService.getProductVar(productService.getProductById(id).get()));
        return "viewProduct";
    }

    @GetMapping("/edit_profile")
    public String updateUser(Model model){
        CustomerDTO currentUser = new CustomerDTO();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails && ((UserDetails) principal).getUsername() != null) {
            String currentUsername = ((UserDetails)principal).getUsername();
            Customer customer = (Customer) customerService.loadUserByUsername(currentUsername);
            currentUser.setId(customer.getId());
            currentUser.setEmail(customer.getEmail());
            currentUser.setPhone(customer.getPhone());
            currentUser.setAddress(customer.getAddress());
            //currentUser.setPassword("");
            currentUser.setName(customer.getName());


//            List<Integer> roleIds = new ArrayList<>();
//            for (Role item:user.getRoles()) {
//                roleIds.add(item.getId());
//            }
//            currentUser.setRoleIds(roleIds);
        }//get current User runtime

        model.addAttribute("customerDTO", currentUser);
        return "editProfile";
    }

    @PostMapping("/edit_profile")
    public String postUserAdd(@ModelAttribute("customerDTO") CustomerDTO customerDTO, Model model) {
        //convert dto > entity
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        //customer.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        customer.setName(customerDTO.getName());
//        List<Role> roles = userService.getUserById(user.getId()).get().getRoles();
//        user.setRoles(roles);
        customerService.updateCustomer(customer);

        Authentication a = SecurityContextHolder.getContext().getAuthentication();


        boolean b =  a instanceof AnonymousAuthenticationToken;
        if(!b){
            String email = a.getName();
            model.addAttribute("customer", customerService.loadUserByUsername(email));
        }
        model.addAttribute("not_authenticated", a instanceof AnonymousAuthenticationToken);
        return "home";
    }
    @GetMapping("/edit_profile/orders")
    public String OrderManage(Model model)
    {
        model.addAttribute("orders",orderService.getOrderByCustomerId(customerService.getCurrentCustomer().getId()));
        return "CustomerOrderManage";
    }
    @GetMapping("/edit_profile/orders/view/{id}")
    public String getOrderEdit(@PathVariable Long id, Model model)
    {
        Order order = orderService.getOrderById(id).get();
        List<OrderedItem> orderedItemList = orderService.getOrderedItem(id);
        model.addAttribute("order",order);
        model.addAttribute("orderitems",orderedItemList);
        return "viewOrderCustomer";
    }

    @GetMapping("/edit_profile/orders/view/{id}/pdf")
    public void exportToPDF(HttpServletResponse response,@PathVariable Long id) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        Order order = orderService.getOrderById(id).get();
        List<OrderedItem> orderedItemList = orderService.getOrderedItem(id);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Order#" + String.valueOf(order.getId()) + ".pdf";
        response.setHeader(headerKey, headerValue);



        OrderPDFExporter exporter = new OrderPDFExporter(orderedItemList,order);
        exporter.export(response);

    }
    @RequestMapping("/shop/search")
    public String searchProducts(Model model,@RequestParam("keyword") String keyword){
        List<Product> listProducts = productService.listAll(keyword);
        model.addAttribute("products", listProducts);
        model.addAttribute("keyword", keyword);
        return "shop";
    }

}
