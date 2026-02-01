package com.example.ptyxiakh.dto;

import java.time.LocalDateTime;

public class OrderDTO {
	private Long id;
	private String fullName;
	private String email;
	private String address;
	private String city;
	private String postal;
	private String number;
	private LocalDateTime date;
	private String productName;
	private Double productPrice;
	private Long sellerId;
	private String sellerUsername;

	public OrderDTO(Long id, String fullName, String email, String address, String city, String postal, String number,
			LocalDateTime date, String productName, Double productPrice) {
		this.setId(id);
		this.setFullName(fullName);
		this.setEmail(email);
		this.setAddress(address);
		this.setCity(city);
		this.setPostal(postal);
		this.setNumber(number);
		this.setDate(date);
		this.setProductName(productName);
		this.setProductPrice(productPrice);
	}

	public OrderDTO(com.example.ptyxiakh.entities.Orders order) {
		this.setId(order.getId());
		this.setFullName(order.getFullName());
		this.setEmail(order.getEmail());
		this.setAddress(order.getAddress());
		this.setCity(order.getCity());
		this.setPostal(order.getPostal());
		this.setNumber(order.getNumber());
		this.setDate(order.getDate());
		this.setProductName(order.getProduct() != null ? order.getProduct().getName() : "Άγνωστο προϊόν");
		this.setProductPrice(order.getProduct() != null ? order.getProduct().getPrice() : null);
		if (order.getProduct() != null && order.getProduct().getSeller() != null) {
			this.setSellerId(order.getProduct().getSeller().getId());
			this.setSellerUsername(order.getProduct().getSeller().getUsername());
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerUsername() {
		return sellerUsername;
	}

	public void setSellerUsername(String sellerUsername) {
		this.sellerUsername = sellerUsername;
	}
}
