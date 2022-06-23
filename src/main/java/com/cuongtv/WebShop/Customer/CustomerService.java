package com.cuongtv.WebShop.Customer;

import com.cuongtv.WebShop.Accounts.Account;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService implements  UserDetailsService{

    private final CustomerRepository customerRepository;

    public String signUpUser(Customer customer){
        customerRepository.save(customer);
        //Todo confirmation
        return "Created customer";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) customerRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("ZZ"));
    }
}
