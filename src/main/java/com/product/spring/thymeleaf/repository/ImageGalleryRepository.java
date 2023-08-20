package com.product.spring.thymeleaf.repository;

import com.product.spring.thymeleaf.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
public interface ImageGalleryRepository extends JpaRepository<ProductDetails, Long>{
    @Transactional
    List<ProductDetails> findByCustomerNameAndOrderDate(String name, Date date);

}

