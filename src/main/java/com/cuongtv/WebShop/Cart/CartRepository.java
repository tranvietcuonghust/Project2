package com.cuongtv.WebShop.Cart;


import com.cuongtv.WebShop.Customer.Customer;
import com.cuongtv.WebShop.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomer(Customer customer);

//    @Query(value="SELECT pd.product_id, pd.image, pd.product_name, pd.description, pd.product_size, count(pd.product_id) as quantity FROM cart_product cp,products pd WHERE cp.cart_id = :cart_id " +
//            "and pd.product_id =  cp.product_product_id " +
//            "GROUP BY product_id, image, product_name, description, product_size",nativeQuery = true)
//    List<Product> findAllProduct(@Param("cart_id") cart_id );
}


