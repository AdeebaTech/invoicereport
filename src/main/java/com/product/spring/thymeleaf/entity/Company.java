package com.product.spring.thymeleaf.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "COMPANYINFO",schema = "mydb")
@Data
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "companyname")
    private String companyname;
    @Column(name = "number")
    private String number;
    @Column(name = "companyaddress1")
    private String companyaddress1;
    @Column(name = "companyaddress2")
    private String companyaddress2;
    @Column(name = "mail")
    private String mail;
    @Column(name = "showroomaddress")
    private String showroomaddress;
    @Column(name = "companyaccount")
    private String companyaccount;
    @Column(name = "accholdername")
    private String accholdername;
    @Column(name = "bankname")
    private String bankname;
    @Column(name = "ifsccode")
    private String ifsccode;

    @Lob
    private byte[] logo;

}
