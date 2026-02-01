package com.example.ptyxiakh.dto;

import com.example.ptyxiakh.entities.User;

public class RegisterRequest {
	private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String role;
    private String accountStatus;
    
    //Method for creating new User, using the DTO
    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setRole(role);
        user.setAccountStatus(accountStatus);
        return user;
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

    public String getConfirmPassword() { 
    	return confirmPassword; 
    	}
    public void setConfirmPassword(String confirmPassword) { 
    	this.confirmPassword = confirmPassword; 
    	}

    public String getFullName() { 
    	return fullName; 
    	}
    public void setFullName(String fullName) { 
    	this.fullName = fullName; 
    	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

}

