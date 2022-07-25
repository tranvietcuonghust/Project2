package com.cuongtv.WebShop.Customer;

import com.cuongtv.WebShop.Accounts.Account;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void updateCustomer(Customer customer) {

            customerRepository.save(customer);

    }
    public Customer getCurrentCustomer(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails && ((UserDetails) principal).getUsername() != null) {
            String currentUsername = ((UserDetails) principal).getUsername();
            Customer customer = (Customer) loadUserByUsername(currentUsername);
            return customer;
        }
        else return null;
    }
    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }
}
