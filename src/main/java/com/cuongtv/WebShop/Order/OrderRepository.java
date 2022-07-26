package com.cuongtv.WebShop.Order;

import com.cuongtv.WebShop.Admin.Statistic;
import com.cuongtv.WebShop.Customer.Customer;
import com.cuongtv.WebShop.Product.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.sql.Date;
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

    @Query(value ="SELECT o.create_date as create_date, count(distinct o.order_id) as numOrder, sum(oi.price) as revenue FROM orders o, ordered_items oi " +
            "WHERE o.order_id = oi.order_id and o.create_date > current_date - 7 GROUP BY o.create_date"
            ,nativeQuery = true)
    public List<Statistic> getRevenue7days();
    @Query(value ="SELECT o.create_date as create_date, count(distinct o.order_id) as numOrder, sum(oi.price) as revenue FROM orders o, ordered_items oi " +
            "WHERE o.order_id = oi.order_id and o.create_date > current_date - 30 GROUP BY o.create_date"
            ,nativeQuery = true)
    public List<Statistic> getRevenue30days();
    @Query(value = "SELECT Top 1 p " +
            "FROM orders o, ordered_items oi, product p " +
            "WHERE o.order_id = oi.ordereditem_id and " +
            "oi.product_id=p.product_id and " +
            "o.create_date > current_date - 7 GROUP BY o.create_date,p.product_id" +
            "ORDER BY sum(oi.price)",nativeQuery = true)
    public Product getTopProduct7days();
    @Query(value = "SELECT Top 1 p " +
            "FROM orders o, ordered_items oi, product p " +
            "WHERE o.order_id = oi.ordereditem_id and " +
            "oi.product_id=p.product_id and " +
            "o.create_date > current_date - 30 GROUP BY o.create_dat,p.product_id" +
            "ORDER BY sum(oi.price)",nativeQuery = true)
    public Product getTopProduct30days();

    @Query(value = "SELECT  Top 1 c " +
            "FROM orders o, ordered_items oi, customer c " +
            "WHERE o.order_id = oi.ordereditem_id and " +
            "o.customer_id=c.customer_id and " +
            "o.create_date > current_date - 7 GROUP BY o.create_date, c.customer_id" +
            "ORDER BY sum(oi.price)",nativeQuery = true)
    public Customer getTopCustomer7days();
    @Query(value = "SELECT Top 1 c " +
            "FROM orders o, ordered_items oi, product p " +
            "WHERE o.order_id = oi.ordereditem_id and " +
            "o.customer_id=p.customer_id and " +
            "o.create_date > current_date - 30 GROUP BY o.create_date, c.customer_id" +
            "ORDER BY sum(oi.price)",nativeQuery = true)
    public Customer getTopCustomer30days();



}
