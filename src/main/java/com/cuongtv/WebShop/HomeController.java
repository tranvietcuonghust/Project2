package com.cuongtv.WebShop;


import com.cuongtv.WebShop.Accounts.Account;
import com.cuongtv.WebShop.Customer.CustomerService;
import com.cuongtv.WebShop.Product.ProductService;
import com.cuongtv.WebShop.registration.RegistrationRequest;
import com.cuongtv.WebShop.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HomeController {
    @Autowired
    private  ProductService productService;
    @Autowired
    private CustomerService customerService;

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
        return "copyhome";
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

    @GetMapping("/adminLogin")
    public String adminLogIn(Model model) {
        return "admin_login";
    }

    @GetMapping("/login_success")
    public String loginSuccess ()

    {
        return "login sucess";
    }

    @GetMapping("/edit_profile")
    public String editProfile(Model model){
        model.addAttribute("userDTO", new RegistrationRequest());
        return"edit_profile";
    }
}
