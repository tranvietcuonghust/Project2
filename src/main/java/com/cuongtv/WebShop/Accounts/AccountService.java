package com.cuongtv.WebShop.Accounts;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {

        private final static  String USER_NOT_FOUND= "User with email %s not found";
        private final AccountRepository accountRepository;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND,email)));
    }

    public String signUpUser(Account account){
        boolean present = accountRepository.findByEmail(account.getEmail()).isPresent();
        if(present){
            throw new IllegalStateException("User exited");
        }
        String encodedPassword=bCryptPasswordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        accountRepository.save(account);
        //Todo confirmation
        return "Created account";
    }

}
