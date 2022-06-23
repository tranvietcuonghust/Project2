package com.cuongtv.WebShop;

import com.cuongtv.WebShop.Accounts.Account;
import com.cuongtv.WebShop.Accounts.AccountRepository;
import com.cuongtv.WebShop.Accounts.AccountRole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class WebShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebShopApplication.class, args);
//
	}

}
