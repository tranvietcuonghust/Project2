package com.cuongtv.WebShop.Cart;

import com.cuongtv.WebShop.Customer.Customer;
import com.cuongtv.WebShop.Order.OrderedItem;
import com.cuongtv.WebShop.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
   public List<Product> listCartItems(Customer customer) {
       Cart cart= cartRepository.findByCustomer(customer).get();
       //Cart cart= cartRepository.findByCustomer(customer).get();

       return cart.getProduct();
   }

   public void createCart(Customer customer){
       Cart cart = new Cart();
       cart.setCustomer(customer);
       cartRepository.save(cart);
   }

   public void addToCart(Customer customer, Product product){

       Cart cart= cartRepository.findByCustomer(customer).get();
       List<Product> productList = cart.getProduct();
       productList.add(product);
       cart.setProduct(productList);
       cartRepository.save(cart);

   }

    public void removeFromCart(Customer customer, int index){

        Cart cart= cartRepository.findByCustomer(customer).get();
        List<Product> productList = cart.getProduct();
        productList.remove(index);
        cart.setProduct(productList);
        cartRepository.save(cart);

    }

    public List<OrderedItem> groupProduct(Customer customer)
    {
        Cart cart= cartRepository.findByCustomer(customer).get();
        List<Product> productList = cart.getProduct();
        Map<Product, Long> productIntegerMap = new HashMap<>();
        for (Product product : productList){
            if(productIntegerMap.containsKey(product)){
                productIntegerMap.replace(product, productIntegerMap.get(product)+1);
            }
            else productIntegerMap.put(product, 1L);
        }
        List<OrderedItem> orderedItemList = new ArrayList<>();
        for (Product product : productIntegerMap.keySet())
        {
            OrderedItem orderedItem = new OrderedItem();
            orderedItem.setProduct(product);
            orderedItem.setQuantity(productIntegerMap.get(product));
            orderedItem.setPrice(productIntegerMap.get(product)*product.getPrice());
            orderedItemList.add(orderedItem);
        }
        return orderedItemList;
    }

    //}
}
