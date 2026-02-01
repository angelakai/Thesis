package com.example.ptyxiakh.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ptyxiakh.Repositories.FurnitureRepository;
import com.example.ptyxiakh.Repositories.UserRepository;
import com.example.ptyxiakh.entities.Furniture;
import com.example.ptyxiakh.entities.User;

@Service
public class FurnitureService {
	// Connecting the repositories needed
	@Autowired
	private FurnitureRepository furnitureRepository;
	@Autowired
	private UserRepository userRepository;

	// Method for finding all ads of the app
	public List<Furniture> getAllFurniture() {
		List<Furniture> furnitureList = furnitureRepository.findAll();
		return furnitureList;
	}

	// Method for finding furniture through id
	public Furniture findById(Long id) {
		return furnitureRepository.findById(id).orElse(null);
	}

	// Method for finding ad list through a seller
	public List<Furniture> getFurnitureBySeller(User seller) {
		return furnitureRepository.findBySeller(seller);
	}

	// Method for filtering ads
	public List<Furniture> getFilteredFurniture(String category, String condition) {
		String state = "available";
		String accountStatus = "ACTIVE";
		// Checking if there is a selected category or condition
		boolean categoryAll = (category == null || category.equals("Όλα"));
		boolean conditionAll = (condition == null || condition.equals("Όλες"));
		// Using the correct method depending on the filters chosen
		if (!categoryAll && !conditionAll) {
			return furnitureRepository.findByStateAndFurnitureCategoryAndFurnitureConditionAndSellerAccountStatus(state,
					category, condition, accountStatus);
		} else if (!categoryAll) {
			return furnitureRepository.findByStateAndFurnitureCategoryAndSellerAccountStatus(state, category,
					accountStatus);
		} else if (!conditionAll) {
			return furnitureRepository.findByStateAndFurnitureConditionAndSellerAccountStatus(state, condition,
					accountStatus);
		} else {
			return furnitureRepository.findByStateAndSellerAccountStatus(state, accountStatus);
		}
	}

	// Method for deleting furniture ad through id
	public void deleteById(Long id) {
		// Finding furniture through id
		Furniture furniture = furnitureRepository.findById(id).orElse(null);
		// Checking if furniture exists
		if (furniture != null) {
			// Finding all users
			List<User> users = userRepository.findAll();
			// Iterating through users
			for (User user : users) {
				// If furniture exists in users favorite, remove it
				if (user.getFavorites().remove(furniture)) {
					// Saving changes
					userRepository.save(user);
				}
			}
			// Deleting furniture
			furnitureRepository.delete(furniture);
		}
	}

	// Method for saving furniture with image
	public Furniture saveFurniture(Furniture furniture, MultipartFile imageFile) throws IOException {
		// Setting the needed image information
		furniture.setImageName(imageFile.getOriginalFilename());
		furniture.setImageType(imageFile.getContentType());
		furniture.setImage(imageFile.getBytes());
		// Saving the ad
		return furnitureRepository.save(furniture);
	}

	// Method for saving ad without given image
	public Furniture saveFurnitureWithoutImage(Furniture furniture) {
		// Checking if image already exists
		if (furniture.getImage() == null || furniture.getImage().length == 0) {
			// Setting the image information to those that the ad already has
			furniture.setImage(furniture.getImage());
			furniture.setImageName(furniture.getImageName());
			furniture.setImageType(furniture.getImageType());
		}
		// Saving the ad
		return furnitureRepository.save(furniture);
	}
}
