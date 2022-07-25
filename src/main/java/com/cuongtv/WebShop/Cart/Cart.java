package com.cuongtv.WebShop.Cart;

import com.cuongtv.WebShop.Customer.Customer;
import com.cuongtv.WebShop.Product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //@Column(name = "created_date")
    //private Date createdDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> product;

   // @JsonIgnore
//   @OneToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "customer_id")
    @OneToOne
    private Customer customer;

    private int quantity;
}
