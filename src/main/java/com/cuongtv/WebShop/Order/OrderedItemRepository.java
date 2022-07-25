package com.cuongtv.WebShop.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {
    @Query(value="SELECT distinct oi.* FROM ordered_items oi WHERE oi.order_id = ?1", nativeQuery = true)
    public List<OrderedItem> findByOrderId(Long id);

}
