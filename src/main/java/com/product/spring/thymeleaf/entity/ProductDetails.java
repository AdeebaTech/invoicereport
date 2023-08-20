package com.product.spring.thymeleaf.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "productdetails",schema = "mydb")
@Data
public class ProductDetails {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	@Column(name = "description", nullable = false)
	private String description;	
	
	@Column(name = "price",nullable = false, precision = 10, scale = 2)
    private int price;

	@Column(name = "customer_name", nullable = false)
	private String customerName;

	@Column(name = "package_charge" ,columnDefinition = "integer default 0")
	private Integer packageCharge;

	@Column(name = "qty",nullable = false, precision = 10, scale = 2)
	private int qty;
	
	@Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;


	@Temporal(TemporalType.DATE)
	@Column(name = "order_date", nullable = false)
	private Date orderDate;

	@Override
	public String toString() {
		return "ImageGallery{" +
				"id=" + id +
				", description='" + description + '\'' +
				", price=" + price +
				", customerName='" + customerName + '\'' +
				", packageCharge=" + packageCharge +
				", qty=" + qty +
				", image=" + Arrays.toString(image) +
				", createDate=" + createDate +
				", orderDate=" + orderDate +
				'}';
	}
}


