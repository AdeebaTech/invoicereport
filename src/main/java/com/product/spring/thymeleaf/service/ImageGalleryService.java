package com.product.spring.thymeleaf.service;

import com.product.spring.thymeleaf.entity.ProductDetails;
import com.product.spring.thymeleaf.repository.ImageGalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ImageGalleryService {

	@Autowired
	private ImageGalleryRepository imageGalleryRepository;
	
	public void saveImage(ProductDetails productDetails) {
		imageGalleryRepository.save(productDetails);
	}

	public List<ProductDetails> getAllActiveImages(String name, Date date) {
		return imageGalleryRepository.findByCustomerNameAndOrderDate(name,date);
	}

	public Optional<ProductDetails> getImageById(Long id) {

		return imageGalleryRepository.findById(id);
	}

	public  void deleteRecords(Long id) {

		imageGalleryRepository.deleteById(id);
	}

	}

