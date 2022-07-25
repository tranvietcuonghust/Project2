package com.cuongtv.WebShop.Order;

import com.cuongtv.WebShop.Customer.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name ="orders")
@Getter
@Setter

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @Column(name = "Name")
    private String Name;
    @Column(name = "Address")
    private String Address;
    @Column(name = "Phone")
    private String Phone;
    @Column(name = "Note")
    private String Note;
    @Column(name = "CreateDate")
    private Date CreateDate;
    @Column(name = "Status")
    private String Status;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderedItem> orderedItems;
}
