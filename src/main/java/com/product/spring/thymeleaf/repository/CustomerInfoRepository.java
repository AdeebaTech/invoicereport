package com.product.spring.thymeleaf.repository;

import javax.transaction.Transactional;

import com.product.spring.thymeleaf.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.product.spring.thymeleaf.entity.Customer;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public interface CustomerInfoRepository extends JpaRepository<Customer, Integer> {
 // List<Customer> findByTitleContainingIgnoreCase(String keyword);

//  @Query("UPDATE Customer t SET t.published = :published WHERE t.id = :id")
//  @Modifying
//  public void updatePublishedStatus(Integer id, boolean published);


    Optional<Customer> findByCustomerName(String customerName);

    @Query(
            value = "select DISTINCT customer_name FROM mydb.customer ",
            nativeQuery = true)
    List getCustomerName();

    @Query(
            value = "select DISTINCT customer_name as  customer_name , to_char(order_date,'dd/MM/yyyy') as  order_date FROM mydb.customer ",
            nativeQuery = true)
    List<Map> getCustomerNameAndOrderDate();

    @Query(
            value = "select DISTINCT customer_name as  customer_name , CONCAT(customer_name,' - ',to_char(order_date,'dd/MM/yyyy')) as  order_date FROM mydb.customer ",
            nativeQuery = true)
    List<Map> concatCustomerNameAndOrderDate();

    @Query(
            value = "select DISTINCT to_char(order_date,'dd/MM/yyyy') FROM mydb.customer ",
            nativeQuery = true)
    List orderDate();


    Optional<Customer> findByCustomerNameAndOrderDate(String name, Date date);


}
