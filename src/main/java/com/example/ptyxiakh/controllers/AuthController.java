package com.example.ptyxiakh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ptyxiakh.dto.LoginRequest;
import com.example.ptyxiakh.dto.RegisterRequest;
import com.example.ptyxiakh.dto.UserDTO;
import com.example.ptyxiakh.entities.User;
import com.example.ptyxiakh.services.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController {
	// Connecting userService to handle user operations
	@Autowired
	private UserService userService;

	// Method for user registration
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
		try {
			// Checks if password and confirm password are the same
			if (!request.getPassword().equals(request.getConfirmPassword())) {
				return ResponseEntity.badRequest().body("Οι κωδικοί πρόσβασης δεν ταιριάζουν");
			}
			// Calling toUser method in order to turn DTO into Entity
			User user = request.toUser();
			// Using the registerUser method to check
			User savedUser = userService.registerUser(user);
			// Setting the password as null
			savedUser.setPassword(null);
			// Sending the user back to frontend
			return ResponseEntity.ok(savedUser);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Σφάλμα εγγραφής: " + e.getMessage());
		}
	}

	// Method for logging in user
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		// Checking if the user information are correct through UserService
		User user = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
		// Checks if information given is correct
		if (user != null) {
			// Setting password as null
			user.setPassword(null);
			// Sending user to frontend
			return ResponseEntity.ok(new UserDTO(user));
		} else {
			// Error messages
			return ResponseEntity.status(401).body("Λανθασμένο username ή password");
		}
	}

}