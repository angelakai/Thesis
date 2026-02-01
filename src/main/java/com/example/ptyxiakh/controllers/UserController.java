package com.example.ptyxiakh.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.ptyxiakh.entities.Furniture;
import com.example.ptyxiakh.entities.User;
import com.example.ptyxiakh.dto.FurnitureDTO;
import com.example.ptyxiakh.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ptyxiakh.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
public class UserController {
	// Connecting userService to handle furniture operations
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	// Method for getting list with all users of the app
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	// Method for getting user through id
	@GetMapping("/profile")
	public ResponseEntity<?> showProfile(@RequestParam("id") Long id) {
		// Finding user
		User user = userService.findById(id);
		// Checking if user was found
		if (user == null) {
			return ResponseEntity.status(404).body("Δεν βρέθηκε χρήστης");
		}
		// Returning user
		UserDTO userDTO = new UserDTO(user);
		return ResponseEntity.ok(userDTO);
	}

	// Method for updating user profile information
	@PutMapping("/updateProfile/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestParam(required = false) String username,
			@RequestParam(required = false) String email, @RequestParam(required = false) String fullName,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) MultipartFile profilePicture) throws IOException {
		// Finding user
		User user = userService.findById(id);
		if (user == null) {
			return ResponseEntity.status(404).body("Δεν βρέθηκε χρήστης");
		}
		// Updating information if given from user
		if (username != null)
			user.setUsername(username);
		if (email != null)
			user.setEmail(email);
		if (fullName != null)
			user.setFullName(fullName);
		if (password != null)
			user.setPassword(passwordEncoder.encode(password));
		// Saving user with image if given by user
		if (profilePicture != null && !profilePicture.isEmpty()) {
			userService.saveUser(user, profilePicture);
		} else {
			// Saving ad without image if not given by user
			userService.saveUserWithoutImage(user);
		}
		return ResponseEntity.ok("Ενημερώθηκε");
	}

	// Getting image of user profile
	@GetMapping("/users/{id}/image")
	public ResponseEntity<byte[]> getUserProfileImage(@PathVariable("id") Long userId) {
		// Finding user
		User user = userService.findById(userId);
		byte[] imageFile = user.getProfilePicture();
		// Giving the content type of the image to ResponseEntity
		return ResponseEntity.ok().contentType(MediaType.valueOf(user.getProfilePictureType())).body(imageFile);
	}

	// Method for finding users favorites ads
	@GetMapping("/users/{id}/favorites")
	public ResponseEntity<?> getFavorites(@PathVariable("id") Long userId) {
		// Finding user
		User user = userService.findById(userId);
		// Checking if user exists
		if (user == null) {
			return ResponseEntity.status(404).body("Δεν βρέθηκε χρήστης");
		}
		// Getting list of favorites 
		Set<Furniture> favorites = user.getFavorites();
		List<FurnitureDTO> favoritesDTO = new ArrayList<>();
		// For each furniture in favorites list, creating a new DTO
		for (Furniture furniture : favorites) {
			favoritesDTO.add(new FurnitureDTO(furniture));
		}
		return ResponseEntity.ok(favoritesDTO);
	}

	// Method for toggling favorite ads
	@PostMapping("/users/{userId}/favorites/{furnitureId}")
	public ResponseEntity<List<FurnitureDTO>> addFavorites(@PathVariable Long userId, @PathVariable Long furnitureId) {
		try {
			userService.addFavorite(userId, furnitureId);
			User user = userService.findById(userId);
			
			// Getting list of favorites 
			Set<Furniture> favorites = user.getFavorites();
			List<FurnitureDTO> favoritesDTO = new ArrayList<>();
			// For each order in orders list, creating a new DTO
			for (Furniture furniture : favorites) {
				favoritesDTO.add(new FurnitureDTO(furniture));
			}

			return ResponseEntity.ok(favoritesDTO);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
