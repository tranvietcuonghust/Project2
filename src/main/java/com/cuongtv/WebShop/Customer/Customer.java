package com.cuongtv.WebShop.Customer;


import com.cuongtv.WebShop.Cart.Cart;
import com.cuongtv.WebShop.Order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table (name="customers")
@Getter
@Setter

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

    @OneToOne(mappedBy = "customer")
    private Cart cart;

    @OneToMany(mappedBy = "customer",
            fetch = FetchType.LAZY)
    private List<Order> orders;

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
