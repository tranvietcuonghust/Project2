package com.cuongtv.WebShop.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value="SELECT p.* FROM products p WHERE p.product_name LIKE %?1%"
            , nativeQuery = true)
    public List<Product> search(String keyword);

}
