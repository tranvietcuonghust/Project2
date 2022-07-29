package com.cuongtv.WebShop.registration;

import com.cuongtv.WebShop.Accounts.Account;
import com.cuongtv.WebShop.Accounts.AccountRole;
import com.cuongtv.WebShop.Accounts.AccountService;
import com.cuongtv.WebShop.Cart.Cart;
import com.cuongtv.WebShop.Cart.CartRepository;
import com.cuongtv.WebShop.Cart.CartService;
import com.cuongtv.WebShop.Customer.Customer;
import com.cuongtv.WebShop.Customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AccountService accountService;
    private final CustomerService customerService;
    private final EmailValidator emailValidator;
    private CartService cartService;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) throw new IllegalStateException("email not valid");
        Account account = new Account(request.getEmail(), request.getPassword(), AccountRole.ROLE_USER);

        accountService.signUpUser(account);

        Customer customer = new Customer();
        customer.setId(account.getId());
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setEmail(request.getEmail());
        customerService.signUpUser(customer);

        cartService.createCart(customer);
        return ("Register completed");
    }
}
