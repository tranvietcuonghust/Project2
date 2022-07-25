package com.cuongtv.WebShop.Customer;


import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {
        private Long id;

        private String email;

        private String password;

        private  String phone;

        private String address;
        //private List<Integer> roleIds;

        private String Name;


}

