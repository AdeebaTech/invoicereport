package com.product.spring.thymeleaf.repository;


import com.product.spring.thymeleaf.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
     @Transactional
    public Optional<Company> findByCompanyname(String name);
}
