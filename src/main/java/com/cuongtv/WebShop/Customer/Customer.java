package com.cuongtv.WebShop.Customer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table (name="customers")
@Getter
@Setter
@AllArgsConstructor
public class Customer implements UserDetails {
    @Id
    @Column (name = "customer_id")
    private Long Id;
    @Column (name = "customer_name")
    private String name;
    @Column (name = "customer_phone")
    private String phone;
    @Column (name = "customer_address")
    private String address;
    @Column (name ="customer_email")
    private String email;


    public Customer() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
