package com.example.ptyxiakh.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "orders")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(name = "fullName", nullable = false)
	private String fullName;
	@NotNull
	@Email
	@Column(name = "email", nullable = false)
	private String email;
	@NotNull
	@Size(min = 4, max = 15)
	@Column(name = "username", nullable = false)
	private String username;
	@NotNull
	@Column(name = "address", nullable = false)
	private String address;
	@NotNull
	@Pattern(regexp = "\\d+", message = "Επιτρέπονται μόνο αριθμοί")
	@Column(name = "number", nullable = false)
	private String number;
	@NotNull
	@Column(name = "city", nullable = false)
	private String city;
	@NotNull
	@Pattern(regexp = "\\d{5}", message = "Ο ταχυδρομικός κώδικας πρέπει να έχει 5 ψηφία")
	@Column(name = "postal", nullable = false)
	private String postal;
	@NotNull
	@Column(name = "date", nullable = false)
	private LocalDateTime date;

	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private User buyer;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Furniture product;

	public Orders() {

	}

	public Orders(String fullName, String email, String username, String address, String number, String city,
			String postal, LocalDateTime date, User buyer, Furniture product) {
		this.fullName = fullName;
		this.email = email;
		this.username = username;
		this.address = address;
		this.number = number;
		this.city = city;
		this.postal = postal;
		this.date = date;
		this.buyer = buyer;
		this.product = product;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public Furniture getProduct() {
		return product;
	}

	public void setProduct(Furniture product) {
		this.product = product;
	}
}