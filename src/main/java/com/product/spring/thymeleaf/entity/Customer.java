package com.product.spring.thymeleaf.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer" ,schema = "mydb")
@Data
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "customer_name", nullable = false)
  private String customerName;

  @Column(name = "customer_add")
  private String address;

  @Column(name = "mobile", nullable = false)
  private String mobile;

  @Temporal(TemporalType.DATE)
  @Column(name = "order_date", nullable = false)
  private Date orderDate;

  @Column(name = "advance" ,columnDefinition = "integer default 0")
  private Integer advance;

  @Column(name = "gst",columnDefinition = "integer default 0")
  private Integer gst;

  @Column(name = "customerBank")
  private String customerBank;

  @Column(name = "orderReviverName")
  private String orderReviverName;

}
