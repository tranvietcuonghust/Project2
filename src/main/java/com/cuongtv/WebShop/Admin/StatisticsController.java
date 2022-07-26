package com.cuongtv.WebShop.Admin;


import com.cuongtv.WebShop.Order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;
import java.util.List;

@Controller
public class StatisticsController {

    @Autowired
    OrderService orderService;

    @GetMapping("/admin/statistics")
    public String chart(Model model){
        List<Statistic>  statistic7days = orderService.getStatistic7days();
        int lenght= statistic7days.size();
        String[] date7days = new String[lenght];
        Long[] numOrder7days= new Long[lenght];
        Long[] revenue7days = new Long[lenght];
        for (int i=0; i<lenght;i++)
        {
            date7days[i]=statistic7days.get(i).getCreate_date().toString();
            numOrder7days[i]=statistic7days.get(i).getNumOrder();
            revenue7days[i]=statistic7days.get(i).getRevenue();
        }
        model.addAttribute("message", "Overview over 7 days");
        model.addAttribute("date7days", date7days);
        model.addAttribute("numOrder7days", numOrder7days);
        model.addAttribute("revenue7days", revenue7days);
        return "chart7days";
    }

    @GetMapping("/chart/7days")
    public String chart7days(Model model){
        List<Statistic>  statistic7days = orderService.getStatistic7days();
        int lenght= statistic7days.size();
        String[] date7days = new String[lenght];
        Long[] numOrder7days= new Long[lenght];
        Long[] revenue7days = new Long[lenght];
        for (int i=0; i<lenght;i++)
        {
            date7days[i]=statistic7days.get(i).getCreate_date().toString();
            numOrder7days[i]=statistic7days.get(i).getNumOrder();
            revenue7days[i]=statistic7days.get(i).getRevenue();
        }

        //model.addAttribute("statistic7days", s7days);
        model.addAttribute("message", "Overview over 7 days");
        model.addAttribute("date7days", date7days);
        model.addAttribute("numOrder7days", numOrder7days);
        model.addAttribute("revenue7days", revenue7days);
        return "chart7days::#display";
    }
    @GetMapping("/chart/30days")
    public String chart30days(Model model){
        List<Statistic>  statistic30days = orderService.getStatistic30days();
        int lenght= statistic30days.size();
        String[] date30days = new String[lenght];
        Long[] numOrder30days= new Long[lenght];
        Long[] revenue30days = new Long[lenght];
        for (int i=0; i<lenght;i++)
        {
            date30days[i]=statistic30days.get(i).getCreate_date().toString();
            numOrder30days[i]=statistic30days.get(i).getNumOrder();
            revenue30days[i]=statistic30days.get(i).getRevenue();
        }

        //model.addAttribute("statistic7days", s7days);
        model.addAttribute("message", "Overview over 30 days");
        model.addAttribute("date7days", date30days);
        model.addAttribute("numOrder7days", numOrder30days);
        model.addAttribute("revenue7days", revenue30days);
        return "chart7days::#display";
    }
}
