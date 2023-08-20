package com.product.spring.thymeleaf.controller;


import com.product.spring.thymeleaf.entity.ProductDetails;
import com.product.spring.thymeleaf.model.CustomerData;
import com.product.spring.thymeleaf.repository.CustomerInfoRepository;
import com.product.spring.thymeleaf.service.ImageGalleryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
public class ImageGalleryController {

    @Value("${uploadDir}")
    private String uploadFolder;

    @Autowired
    private ImageGalleryService imageGalleryService;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

//	@GetMapping(value = {"/", "/home"})
//	public String addProductPage() {
//		return "index";
//	}

    @PostMapping("/image/saveImageDetails")
    public @ResponseBody ResponseEntity<?> createProduct(
                                                         @RequestParam("price") int price, @RequestParam("description") String description,
                                                         @RequestParam("customer_name") String customer_name, @RequestParam("package_charge") int package_charge,
                                                         @RequestParam("qty") int qty, @RequestParam("order_date") String orderDate,
                                                         HttpServletRequest request, final @RequestParam("image") MultipartFile file) {
        try {
            String[] cus_name = customer_name.split(",");
            String[] order_date = orderDate.split(",");
            String [] orderDateAndName = order_date[0].split(" - ");
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter1.parse(orderDateAndName[1]);

                String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
                log.info("uploadDirectory:: " + uploadDirectory);
                String fileName = file.getOriginalFilename();
                String filePath = Paths.get(uploadDirectory, fileName).toString();
                log.info("FileName: " + file.getOriginalFilename());
//                if (fileName == null || fileName.contains("..")) {
//                    model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
//                    return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
//                }
                String[] descriptions = description.split(",");
                Date createDate = new Date();
                byte[] imageData = file.getBytes();
                ProductDetails productDetails = new ProductDetails();
                productDetails.setImage(imageData);
                productDetails.setPrice(price);
                productDetails.setDescription(descriptions[0]);
                productDetails.setCreateDate(createDate);
                productDetails.setCustomerName(orderDateAndName[0].toUpperCase());
                productDetails.setPackageCharge(package_charge);
                productDetails.setQty(qty);
                productDetails.setOrderDate(date);
                imageGalleryService.saveImage(productDetails);

                log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
                return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/image/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<ProductDetails> imageGallery)
            throws ServletException, IOException {
        log.info("Id :: " + id);
        imageGallery = imageGalleryService.getImageById(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(imageGallery.get().getImage());
        response.getOutputStream().close();
    }

    @GetMapping("/image/imageDetails")
    String showProductDetails(@RequestParam("id") Long id, Optional<ProductDetails> imageGallery, Model model) {
        try {
            log.info("Id :: " + id);
            if (id != 0) {
                imageGallery = imageGalleryService.getImageById(id);

                log.info("products :: " + imageGallery);
                if (imageGallery.isPresent()) {
                    model.addAttribute("id", imageGallery.get().getId());
                    model.addAttribute("description", imageGallery.get().getDescription());
                    model.addAttribute("price", imageGallery.get().getPrice());
                    return "imagedetails";
                }
                return "redirect:/home";
            }
            return "redirect:/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home";
        }
    }

    @GetMapping("/image/show")
    String show(Model map, CustomerData data, RedirectAttributes redirectAttributes) {
        String link = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String [] name_date = data.getCustomerName().split(" - ");
            Date date1 = formatter.parse(name_date[1]);
            CustomerData customerData = new CustomerData();
            List<ProductDetails> images = imageGalleryService.getAllActiveImages(name_date[0].toUpperCase(), date1);
            if (images.size() > 0 && images != null) {
                map.addAttribute("images", images);
               // map.addAttribute("tutorial", customerData);
                link = "deleteProductDetails";

            } else {
               // map.addAttribute("tutorial", data);
                link = "deleteProductDetails";

            }
        } catch (Exception e) {
            map.addAttribute("tutorial", data);
            link = "deleteProductDetails";
        }

        return link;
    }

    @PostMapping("/image/deleteProduct")
    String deleteProduct(@RequestParam("id") Long id, CustomerData data, Model model) {
        try {
            log.info("Id :: " + id);
            if (id != 0) {
                Optional<ProductDetails> cusData = imageGalleryService.getImageById(id);
                data.setCustomerName(cusData.get().getCustomerName());
                SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
                String date = formatter1.format(cusData.get().getOrderDate());
                data.setOrderDate(date);
                imageGalleryService.deleteRecords(id);
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                Date date1 = formatter.parse(date);
                List<ProductDetails> images = imageGalleryService.getAllActiveImages(data.getCustomerName().toUpperCase(), cusData.get().getOrderDate());

                    model.addAttribute("images", images);
                    model.addAttribute("message", "Record has been deleted successfully!");
                    model.addAttribute("tutorial", data);

            }
            return "productDetails";
        } catch (Exception e) {
            model.addAttribute("message", "Record not deleted successfully!");
            e.printStackTrace();
            return "productDetails";
        }
    }
}	

