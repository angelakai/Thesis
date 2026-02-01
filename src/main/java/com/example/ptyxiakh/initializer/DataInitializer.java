package com.example.ptyxiakh.initializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.ptyxiakh.Repositories.UserRepository;
import com.example.ptyxiakh.entities.User;

@Component
public class DataInitializer implements CommandLineRunner {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) {
		if (userRepository.findByRole("ADMIN").isEmpty()) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setFullName("Admin");
			admin.setEmail("admin@gmail.com");
			admin.setPassword(passwordEncoder.encode("admin123!"));
			admin.setRole("ADMIN");
			admin.setAccountStatus("ACTIVE");
			try {
				byte[] imageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/images/default.jpg"));
				admin.setProfilePicture(imageBytes);
				admin.setProfilePictureName("default.jpg");
				admin.setProfilePictureType("image/jpeg");
			} catch (IOException e) {
				System.err.println("Failed to load default image: " + e.getMessage());
			}
			userRepository.save(admin);
			System.out.println("Admin user created.");
		} else {
			System.out.println("Admin user already exists.");
		}

	}

}
