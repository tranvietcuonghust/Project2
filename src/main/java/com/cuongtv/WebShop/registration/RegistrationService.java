package com.cuongtv.WebShop.registration;

import com.cuongtv.WebShop.Accounts.Account;
import com.cuongtv.WebShop.Accounts.AccountRole;
import com.cuongtv.WebShop.Accounts.AccountService;
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
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) throw new IllegalStateException("email not valid");
        Account account = new Account(request.getEmail(), request.getPassword(), AccountRole.USER);

        accountService.signUpUser(account);
        Customer customer = new Customer(account.getId(), request.getName(),request.getPhone(), request.getAddress(),request.getEmail() );
        customerService.signUpUser(customer);
        return ("Register completed");
    }
}
