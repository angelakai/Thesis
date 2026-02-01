package com.example.ptyxiakh.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.example.ptyxiakh.entities.Furniture;
import com.example.ptyxiakh.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ptyxiakh.Repositories.FurnitureRepository;
import com.example.ptyxiakh.Repositories.UserRepository;

@Service
public class UserService {
	// Connecting the repositories needed
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FurnitureRepository furnitureRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	// Method for finding all users of the app
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	// Method for finding a user through their id
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	// Method for finding user through their username
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	// Method for disabling user through their id
	public void disableUserById(Long id) {
		// Finding user from id
		User user = userRepository.findById(id).orElse(null);
		// Checking if user exists
		if (user != null) {
			// Clearing favorites list
			user.getFavorites().clear();
			user.setAccountStatus("INACTIVE");
		}
		// Disabling user
		userRepository.save(user);
	}
	// Method for enabling user through their id
	public void enableUserById(Long id) {
		// Finding user from id
		User user = userRepository.findById(id).orElse(null);
		// Checking if user exists
		if (user != null) {
			// Clearing favorites list
			user.setAccountStatus("ACTIVE");
		}
		// Enabling user
		userRepository.save(user);
	}
	// Method for registration. It creates a new user if it doesn't already exist
	public User registerUser(User user) throws Exception {
		// Loading users username using Optional type in case it's not already in use
		Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
		// Checking if the user with the username already exists
		if (existingUser.isPresent()) {
			throw new Exception("Το όνομα χρήστη χρησιμοποιείται ήδη");
		}
		// Loading users email using Optional type in case it's not already in use
		Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());
		// Checking if the user with the email already exists
		if (existingEmail.isPresent()) {
			throw new Exception("Το email χρησιμοποιείται ήδη");
		}
		// Encoding password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("USER");
		user.setAccountStatus("ACTIVE");
		// Saving user, using the default profile picture
		return saveUserWithoutImage(user);
	}

	// Method for logging in, checking if the given information are correct for the
	// authentication
	public User authenticateUser(String username, String password) {
		// Loading users username using Optional type in case it's not already in use
		Optional<User> user = userRepository.findByUsername(username);
		// Checking if the user already exists and the password encoded matches the
		// already encoded password from the Database
		if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())  && "ACTIVE".equals(user.get().getAccountStatus())) {
			// Returning the user
			return user.get();
		} else {
			return null;
		}
	}

	// Method for saving user with their chosen profile picture
	public User saveUser(User user, MultipartFile image) throws IOException {
		// Checking if user input for profile image exists
		if (image != null && !image.isEmpty()) {
			// Setting the data needed for the image
			user.setProfilePicture(image.getBytes());
			user.setProfilePictureName(image.getOriginalFilename());
			user.setProfilePictureType(image.getContentType());
		}
		// Saving user
		return userRepository.save(user);
	}

	// Method for saving user with a default profile picture
	public User saveUserWithoutImage(User user) {
		try {
			// Checking if user has a photo profile
			if (user.getProfilePicture() == null || user.getProfilePicture().length == 0) {
				// Setting the needed data for the default photo
				ClassPathResource defaultImage = new ClassPathResource("static/images/default.jpg");
				user.setProfilePicture(defaultImage.getInputStream().readAllBytes());
				user.setProfilePictureName("default.jpg");
				user.setProfilePictureType("image/jpg");
			}
		} catch (IOException e) {
			System.err.println("Αποτυχία φόρτωσης προεπιλεγμένης εικόνας: " + e.getMessage());
		}
		// Saving user
		return userRepository.save(user);
	}

	// Method for toggling an ad to the users favorites list
	public void addFavorite(Long userId, Long furnitureId) {
		// Finding user through id
		User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
		// Finding furniture through id
		Furniture furniture = furnitureRepository.findById(furnitureId).orElseThrow(IllegalArgumentException::new);
		// Checking if users favorites list already contains the furniture given
		if (user.getFavorites().contains(furniture)) {
			// Removing the given furniture from users favorites list
			user.getFavorites().remove(furniture);
		} else {
			// Adding furniture to users favorites
			user.getFavorites().add(furniture);
		}
		// Saving user information
		userRepository.save(user);
	}

}
