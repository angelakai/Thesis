package com.example.ptyxiakh.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "username", nullable = false, unique = true)
	@Size(min = 4, max = 15)
	private String username;

	@NotNull
	@Email
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@NotNull
	@Column(name = "fullName", nullable = false)
	private String fullName;
	@NotNull
	@Column(name = "role", nullable = false)
	private String role;
	@NotNull
	@Column(name = "account_status", nullable = false)
	private String accountStatus;
	@ManyToMany
	@JoinTable(name = "user_favorites", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "furniture_id"))
	private Set<Furniture> favorites = new LinkedHashSet<>();
	@Lob
	@NotNull
	@Column(name = "profilePicture", nullable = false, columnDefinition = "MEDIUMBLOB")
	private byte[] profilePicture;
	@NotNull
	@Column(name = "profilePictureName", nullable = false)
	private String profilePictureName;
	@NotNull
	@Column(name = "profilePictureType", nullable = false)
	private String profilePictureType;
	@NotNull
	@Column(name = "password", nullable = false)
	private String password;

	@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Furniture> furnitures = new LinkedHashSet<>();

	@OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Orders> orders = new LinkedHashSet<>();

	public User() {

	}

	public User(String username, String email, String password, String fullName, String role, String accountStatus) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.role = role;
		this.accountStatus = accountStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getProfilePictureName() {
		return profilePictureName;
	}

	public void setProfilePictureName(String profilePictureName) {
		this.profilePictureName = profilePictureName;
	}

	public String getProfilePictureType() {
		return profilePictureType;
	}

	public void setProfilePictureType(String profilePictureType) {
		this.profilePictureType = profilePictureType;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Set<Furniture> getFavorites() {
		return favorites;
	}

	public void setFavorites(Set<Furniture> favorites) {
		this.favorites = favorites;
	}

	public Set<Furniture> getFurnitures() {
		return furnitures;
	}

	public void setFurnitures(Set<Furniture> furnitures) {
		this.furnitures = furnitures;
	}

	public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}

	public void addFavorite(Furniture furniture) {
		favorites.add(furniture);
		furniture.getFavorited().add(this);
	}

	public void removeFavorite(Furniture furniture) {
		favorites.remove(furniture);
		furniture.getFavorited().remove(this);
	}

}
