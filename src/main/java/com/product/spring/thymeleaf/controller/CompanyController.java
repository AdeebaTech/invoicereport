package com.product.spring.thymeleaf.controller;

import com.product.spring.thymeleaf.entity.Company;
import com.product.spring.thymeleaf.entity.Customer;
import com.product.spring.thymeleaf.repository.CompanyRepository;
import com.product.spring.thymeleaf.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/company/details")
    public String companyDetails(Model model) {

        return "companyInfo";
    }

    @PostMapping("/company/saveCompanyDetails")
    public @ResponseBody ResponseEntity<?> createProduct(@RequestParam("company_name") String company_name,
                                                         @RequestParam("com_address1") String com_address1, @RequestParam("com_address2") String com_address2,
                                                         @RequestParam("com_offAddress") String com_offAddress, @RequestParam("number") String number,
                                                         @RequestParam("mail_id") String mail_id, @RequestParam("ac") String ac, @RequestParam("bank") String bank,
                                                         @RequestParam("ifsc") String ifsc, @RequestParam("ac_holder") String ac_hoder,
                                                         HttpServletRequest request, final @RequestParam("image") MultipartFile file) {
        try {
            String[] comp_name = company_name.split(",");
            String[] address1 = com_address1.split(",");
            String[] address2 = com_address2.split(",");
            String[] offAddress = com_offAddress.split(",");
            String[] compNumber = number.split(",");
            String[] mailId = mail_id.split(",");
            String[] account = ac.split(",");
            String[] bankName = bank.split(",");
            String[] ifscCode = ifsc.split(",");
            String[] acHolder = ac_hoder.split(",");
            Company company = new Company();

            company.setCompanyname(comp_name[0]);
            company.setLogo(file.getBytes());
            company.setCompanyaddress1(address1[0]);
            company.setCompanyaddress2(address2[0]);
            company.setShowroomaddress(offAddress[0]);
            company.setMail(mailId[0]);
            company.setNumber(compNumber[0]);
            company.setCompanyaccount(account[0]);

            company.setBankname(bankName[0]);
            company.setAccholdername(acHolder[0]);
            company.setIfsccode(ifscCode[0]);

            companyRepository.save(company);

            return new ResponseEntity<>("Company data save Successfully ", HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

    }


//        @PostMapping
//    public ResponseEntity<String> saveCompanyDetails(@RequestParam("logo") MultipartFile file, @RequestParam("data") String data) {
//        try {
//          //  companyService.save(file,data);
//
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
//        }
//    }

}
