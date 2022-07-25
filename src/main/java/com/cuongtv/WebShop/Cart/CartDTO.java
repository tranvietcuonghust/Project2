package com.cuongtv.WebShop.Cart;



import com.cuongtv.WebShop.Product.Product;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {
        //tao bien toan cuc
        private Long CustomerID;
        public static List<Product> cart;

        static {
            cart = new ArrayList<>();
        }




    }
