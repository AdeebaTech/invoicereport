package com.product.spring.thymeleaf.service;


import com.product.spring.thymeleaf.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

//    public void save(MultipartFile file, String data) throws IOException {
//        Gson g = new Gson();
//        Map jsondata = g.fromJson(data,Map.class);
//        Company company = new Company();
//
//        company.setCompanyname(jsondata.get("companyname").toString());
//        company.setLogo(file.getBytes());
//        company.setCompanyaddress1(jsondata.get("companyaddress1").toString());
//        company.setCompanyaddress2(jsondata.get("companyaddress2").toString());
//        company.setShowroomaddress(jsondata.get("showroomaddress").toString());
//        company.setMail(jsondata.get("mail").toString());
//        company.setNumber(jsondata.get("number").toString());
//        company.setCompanyaccount(jsondata.get("ac").toString());
//
//        company.setBankname(jsondata.get("bankname").toString());
//        company.setAccholdername(jsondata.get("acholder").toString());
//        company.setIfsccode(jsondata.get("ifsc").toString());
//        companyRepository.saveAndFlush(company);
//    }
}
