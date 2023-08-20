package com.product.spring.thymeleaf.model;

import lombok.Data;

@Data
public class CustomerData {
    private String customerName;
    private String orderDate;
    private String address;
    private String mobile;
    private Integer advance;
    private Integer gst;
}
