package com.cuongtv.WebShop.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductVarRepository extends JpaRepository<ProductVar, Long> {
    @Query(value="SELECT pv.* FROM productvars pv WHERE cast(pv.product_id as varchar(255)) LIKE %?1%"
            , nativeQuery = true)
    public List<ProductVar> findByProductId(Long productId);
}
