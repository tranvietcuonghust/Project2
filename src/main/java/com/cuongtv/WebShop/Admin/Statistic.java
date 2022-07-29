package com.cuongtv.WebShop.Admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public interface Statistic {
    Date getCreate_date();
    Long getNumOrder();
    Long getRevenue();
}
