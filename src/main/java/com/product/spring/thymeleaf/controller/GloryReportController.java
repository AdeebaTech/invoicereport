package com.product.spring.thymeleaf.controller;

import com.product.spring.thymeleaf.entity.Customer;
import com.product.spring.thymeleaf.model.CustomerData;
import com.product.spring.thymeleaf.repository.CustomerInfoRepository;
import com.product.spring.thymeleaf.service.GloryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class GloryReportController {

    @Autowired
    private GloryService gloryService;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @GetMapping("/report/invoiceReport")
    public String productDetails(Model model) {
        Customer customer = new Customer();
        model.addAttribute("report", customer);
        List<Map> list = customerInfoRepository.concatCustomerNameAndOrderDate();
        model.addAttribute("customerList",list);
        return "invoiceReport";
    }

    @RequestMapping(value = "/invoiceReport", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<byte[]> getStaffWiseExamDutyDetailReport(HttpServletResponse response1, CustomerData data)  {
        ResponseEntity<byte[]> response = null;
        byte[] invData = null;


        response1.setContentType("application/pdf");
        response1.setHeader("Content-Disposition", "attachment; filename=\"GloryReport.pdf\"");
        ByteArrayOutputStream bstream = new ByteArrayOutputStream();
        try {
            String [] name_date = data.getCustomerName().split(" - ");

            invData = gloryService.getGloryReport(name_date[0],name_date[1], response1);
            response = new ResponseEntity<>(invData, HttpStatus.OK);
            return response;
        } catch (Exception e) {
            return new ResponseEntity<>(bstream.toByteArray(), HttpStatus.EXPECTATION_FAILED);
        }

    }
}
