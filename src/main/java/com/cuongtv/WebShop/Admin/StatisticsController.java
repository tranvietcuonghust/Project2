package com.cuongtv.WebShop.Admin;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {


    @GetMapping("/test")
    public String test(Model model){
        int[] xValues = new int[]{50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160};
        int[] yValues = new int[]{7, 8, 8, 9, 9, 9, 10, 11, 14, 14, 15, 8};

        model.addAttribute("xValues", xValues);
        model.addAttribute("yValues", yValues);
        return "chart";
    }
}
