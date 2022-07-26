package com.cuongtv.WebShop.Cart;

import com.cuongtv.WebShop.Customer.Customer;
import com.cuongtv.WebShop.Order.OrderedItem;
import com.cuongtv.WebShop.Product.Product;
import com.cuongtv.WebShop.Product.ProductVar;
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
    public List<ProductVar> listCartItems(Customer customer) {
       Cart cart= cartRepository.findByCustomer(customer).get();
       //Cart cart= cartRepository.findByCustomer(customer).get();

       return cart.getProductvar();
   }

   public void createCart(Customer customer){
       Cart cart = new Cart();
       cart.setCustomer(customer);
       cartRepository.save(cart);
   }

   public void addToCart(Customer customer, ProductVar productVar){

       Cart cart= cartRepository.findByCustomer(customer).get();
       List<ProductVar> productList = cart.getProductvar();
       productList.add(productVar);
       cart.setProductvar(productList);
       cartRepository.save(cart);

   }

    public void removeFromCart(Customer customer, int index){

        Cart cart= cartRepository.findByCustomer(customer).get();
        List<ProductVar> productList = cart.getProductvar();
        productList.remove(index);
        cart.setProductvar(productList);
        cartRepository.save(cart);

    }

    public List<OrderedItem> groupProduct(Customer customer)
    {
        Cart cart= cartRepository.findByCustomer(customer).get();
        List<ProductVar> productList = cart.getProductvar();
        Map<ProductVar, Long> productIntegerMap = new HashMap<>();
        for (ProductVar product : productList){
            if(productIntegerMap.containsKey(product)){
                productIntegerMap.replace(product, productIntegerMap.get(product)+1);
            }
            else productIntegerMap.put(product, 1L);
        }
        List<OrderedItem> orderedItemList = new ArrayList<>();
        for (ProductVar product : productIntegerMap.keySet())
        {
            OrderedItem orderedItem = new OrderedItem();
            orderedItem.setProductvar(product);
            orderedItem.setQuantity(productIntegerMap.get(product));
            orderedItem.setPrice(productIntegerMap.get(product)*product.getProduct().getPrice());
            orderedItemList.add(orderedItem);
        }
        return orderedItemList;
    }

    //}
}
