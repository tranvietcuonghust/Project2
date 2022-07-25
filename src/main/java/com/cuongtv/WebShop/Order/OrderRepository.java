package com.cuongtv.WebShop.Order;

import com.cuongtv.WebShop.Product.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    //List<Order> findAllByUserIdOrderByCreatedDateDesc(Long userId);
    @Query(value="SELECT distinct o.* FROM orders o WHERE o.customer_id = ?1", nativeQuery = true)
    public List<Order> findByCustomerId(Long id);
    @Query(value="SELECT o.* FROM orders o WHERE o.order_id LIKE %?1%"
            , nativeQuery = true)
    public List<Order> searchById(String keyword);
    @Query(value="SELECT o.* FROM orders o WHERE o.phone LIKE %?1%"
            , nativeQuery = true)
    public List<Order> searchByPhone(String keyword);
    @Query(value="SELECT o.* FROM orders o WHERE o.name LIKE %?1%"
            , nativeQuery = true)
    public List<Order> searchByName(String keyword);
    @Query(value = "SELECT distinct o.* FROM orders o WHERE (:id is null or CAST(o.order_id as varchar(255))LIKE %:id%) and" +
            "(:Name is null or o.name LIKE %:Name%) and" +
            "(:Phone is null or o.name LIKE %:Phone%) and" +
            "(:status is null or o.status LIKE %:status%)"
                    ,nativeQuery = true)
    public List<Order> multiSearch(String id, String Name, String Phone, String status);
}
