package com.example.ptyxiakh.dto;

public class UserDTO {
	private Long id;
	private String fullName;
	private String username;
	private String email;
	private String imageUrl;
	private String role;
	private String accountStatus;

	public UserDTO(Long id, String fullName, String username, String email, String imageUrl, String role, String accountStatus) {
		this.setId(id);
		this.setFullName(fullName);
		this.setUsername(username);
		this.setEmail(email);
		this.setImageUrl(imageUrl);
		this.setRole(role);
		this.setAccountStatus(accountStatus);
	}

	public UserDTO(com.example.ptyxiakh.entities.User user) {
		this.setId(user.getId());
		this.setFullName(user.getFullName());
		this.setUsername(user.getUsername());
		this.setEmail(user.getEmail());
		this.setImageUrl("http://localhost:8081/user/users/" + user.getId() + "/image");
		this.setRole(user.getRole());
		this.setAccountStatus(user.getAccountStatus());
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
}
