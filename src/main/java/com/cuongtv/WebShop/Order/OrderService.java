package com.cuongtv.WebShop.Order;


import com.cuongtv.WebShop.Cart.CartService;
import com.cuongtv.WebShop.Customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    public OrderRepository orderRepository;
    @Autowired
    public OrderedItemRepository orderedItemRepository;

    @Autowired
    private CartService cartService;

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }
    public void placeOrder(OrderDTO orderDTO, Customer customer)
    {
        Order order = new Order();
        order.setName(orderDTO.getName());
        order.setAddress(orderDTO.getAddress());
        order.setCustomer(customer);
        order.setPhone(orderDTO.getPhone());
        order.setNote(orderDTO.getNote());
        order.setOrderedItems(cartService.groupProduct(customer));

        order.setCreateDate(new Date(System.currentTimeMillis()));
        order.setStatus("CREATED");
        //order.setCreateDate(new Date());
        orderRepository.save(order);
        for (OrderedItem orderedItem : order.getOrderedItems()){
            orderedItem.setOrder(order);
        }
        orderedItemRepository.saveAll(order.getOrderedItems());
    }
    public Optional<Order> getOrderById(Long Id)
    {
        return orderRepository.findById(Id);
    }
    public List<OrderedItem> getOrderedItem (Long id){return orderedItemRepository.findByOrderId(id);}
    public List<Order> getOrderByCustomerId(Long id){return orderRepository.findByCustomerId(id);}
    public List<Order> searchById(String keyword){ return orderRepository.searchById(keyword);}
    public List<Order> searchByPhone(String keyword){ return orderRepository.searchByPhone(keyword);}
    public List<Order> searchByName(String keyword){ return orderRepository.searchByName(keyword);}
    public List<Order> multiSearch(String id, String phone, String name, String status)
    { return orderRepository.multiSearch(id, phone, name, status);}
    public void updateOrder(Order order) {
        Order oldOrder = orderRepository.findById(order.getId()).get();
        oldOrder.setStatus(order.getStatus());
        orderRepository.save(oldOrder);
    }



}
