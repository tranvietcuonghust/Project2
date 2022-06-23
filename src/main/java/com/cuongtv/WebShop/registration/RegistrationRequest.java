package com.cuongtv.WebShop.registration;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegistrationRequest {
    private  String name;
    private  String phone;
    private  String address;
    private  String email;
    private  String password;

}
