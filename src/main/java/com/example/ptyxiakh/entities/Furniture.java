package com.example.ptyxiakh.entities;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "furniture")
public class Furniture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(name = "name", nullable = false)
	private String name;
	@NotNull
	@Column(name = "description", nullable = false)
	private String description;

	@Positive
	@NotNull
	@Column(name = "price", nullable = false)
	private Double price;
	@NotNull
	@Column(name = "furnitureCondition", nullable = false)
	private String furnitureCondition;
	@NotNull
	@Column(name = "furnitureCategory", nullable = false)
	private String furnitureCategory;
	@NotNull
	@Column(name = "state", nullable = false)
	private String state;

	@Lob
	@NotNull
	@Column(name = "image", nullable = false, columnDefinition = "MEDIUMBLOB")
	private byte[] image;
	@NotNull
	@Column(name = "imageName", nullable = false)
	private String imageName;
	@NotNull
	@Column(name = "imageType", nullable = false)
	private String imageType;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User seller;
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Orders order;
	@ManyToMany(mappedBy = "favorites")
	private Set<User> favorited = new LinkedHashSet<>();

	public Furniture() {

	}

	public Furniture(String name, String description, Double price, String furnitureCondition, String furnitureCategory,
			User seller, String state) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.furnitureCondition = furnitureCondition;
		this.furnitureCategory = furnitureCategory;
		this.seller = seller;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getFurnitureCondition() {
		return furnitureCondition;
	}

	public void setFurnitureCondition(String furnitureCondition) {
		this.furnitureCondition = furnitureCondition;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public String getFurnitureCategory() {
		return furnitureCategory;
	}

	public void setFurnitureCategory(String furnitureCategory) {
		this.furnitureCategory = furnitureCategory;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getState() {
		return state;
	}

	public void setState(String status) {
		this.state = status;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public Set<User> getFavorited() {
		return favorited;
	}

	public void setFavorited(Set<User> favorited) {
		this.favorited = favorited;
	}

	public void addFavorited(User user) {
		favorited.add(user);
		user.getFavorites().add(this);
	}

	public void removeFavorited(User user) {
		favorited.remove(user);
		user.getFavorites().remove(this);
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

}
