package com.product.spring.thymeleaf.controller;

import com.product.spring.thymeleaf.entity.Customer;
import com.product.spring.thymeleaf.model.CustomerData;
import com.product.spring.thymeleaf.repository.CustomerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerInfoController {

  @Autowired
  private CustomerInfoRepository customerInfoRepository;

  @GetMapping("/customerInfo")
  public String getAll(Model model, @Param("keyword") String keyword) {
    try {
//      List<Tutorial> tutorials = new ArrayList<Tutorial>();
//
//      if (keyword == null) {
//        tutorialRepository.findAll().forEach(tutorials::add);
//      } else {
//        tutorialRepository.findByTitleContainingIgnoreCase(keyword).forEach(tutorials::add);
//        model.addAttribute("keyword", keyword);
//      }
//
//      model.addAttribute("tutorial", tutorials);
      Customer customer = new Customer();

      model.addAttribute("customer", customer);
      model.addAttribute("pageTitle", "Customer Information");
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }

    return "customerInfo";
  }

  @GetMapping("/customerInfo/product")
  public String addTutorial(Model model) {
    Customer customer = new Customer();
    model.addAttribute("tutorial", customer);
    model.addAttribute("pageTitle", "Customer Information");
    List<Map> list = customerInfoRepository.concatCustomerNameAndOrderDate();
   // List dateList = customerInfoRepository.orderDate();
    model.addAttribute("customerList",list);
   // model.addAttribute("orderDates",dateList);
    return "productInfo";
  }

  @GetMapping("/customerInfo/deleteProductDetails")
  public String productDetails(Model model) {
    Customer customer = new Customer();
    model.addAttribute("tutorial", customer);
    List<Map> list = customerInfoRepository.concatCustomerNameAndOrderDate();
    model.addAttribute("customerList",list);
    return "deleteProductDetails";
  }

  @PostMapping("/customerInfo/save")
  public String saveTutorial(CustomerData customer, RedirectAttributes redirectAttributes) {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      Date date1 = formatter.parse(customer.getOrderDate());
      Customer customer1 = new Customer();
      customer1.setCustomerName(customer.getCustomerName().toUpperCase());
      customer1.setAddress(customer.getAddress());
      customer1.setMobile(customer.getMobile());
      customer1.setOrderDate(date1);
      customer1.setCustomerBank(customer.getCustomerBank());


      customerInfoRepository.save(customer1);

      redirectAttributes.addFlashAttribute("message", "The Customer Info has been saved successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      redirectAttributes.addAttribute("message", e.getMessage());
    }

    return "redirect:/customerInfo";
  }

//  @GetMapping("/tutorials/{id}")
//  public String editTutorial(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
//    try {
//      Customer customer = customerInfoRepository.findById(id).get();
//
//      model.addAttribute("tutorial", customer);
//      model.addAttribute("pageTitle", "Edit Tutorial (ID: " + id + ")");
//
//      return "tutorial_form";
//    } catch (Exception e) {
//      redirectAttributes.addFlashAttribute("message", e.getMessage());
//
//      return "redirect:/tutorials";
//    }
//  }

  @GetMapping("/tutorials/delete/{id}")
  public String deleteTutorial(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      customerInfoRepository.deleteById(id);

      redirectAttributes.addFlashAttribute("message", "The Tutorial with id=" + id + " has been deleted successfully!");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/tutorials";
  }

//  @GetMapping("/tutorials/{id}/published/{status}")
//  public String updateTutorialPublishedStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean published,
//      Model model, RedirectAttributes redirectAttributes) {
//    try {
//      tutorialRepository.updatePublishedStatus(id, published);
//
//      String status = published ? "published" : "disabled";
//      String message = "The Tutorial id=" + id + " has been " + status;
//
//      redirectAttributes.addFlashAttribute("message", message);
//    } catch (Exception e) {
//      redirectAttributes.addFlashAttribute("message", e.getMessage());
//    }

    //return "redirect:/tutorials";
  //}
}
