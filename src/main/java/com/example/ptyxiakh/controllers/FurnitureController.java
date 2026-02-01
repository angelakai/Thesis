package com.example.ptyxiakh.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ptyxiakh.dto.FurnitureDTO;
import com.example.ptyxiakh.entities.Furniture;
import com.example.ptyxiakh.entities.User;
import com.example.ptyxiakh.services.FurnitureService;
import com.example.ptyxiakh.services.UserService;

@RestController
@RequestMapping("/furniture")
@CrossOrigin("http://localhost:3000")
public class FurnitureController {

	// Connecting userService to handle furniture operations
	@Autowired
	private FurnitureService furnitureService;

	// Connecting userService to handle user operations
	@Autowired
	private UserService userService;

	// Method for creating furniture ad
	@PostMapping("/create")
	public ResponseEntity<?> createFurniture(@RequestPart Furniture furniture, @RequestPart MultipartFile image) {
		try {
			// Checking if seller exists
			if (furniture.getSeller() == null || furniture.getSeller().getId() == null) {
				return ResponseEntity.badRequest().body("Δεν βρέθηκε id χρήστη");
			}
			// Getting seller from the database
			User seller = userService.findById(furniture.getSeller().getId());
			if (seller == null) {
				return ResponseEntity.badRequest().body("Δεν βρέθηκε χρήστης");
			}
			// Setting seller and ad state
			furniture.setSeller(seller);
			furniture.setState("available");
			// Saving ad with image
			Furniture savedFurniture = furnitureService.saveFurniture(furniture, image);
			return ResponseEntity.ok(savedFurniture);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Σφάλμα δημιουργίας " + e.getMessage());
		}
	}

	// Method for getting furniture details from specific ad
	@GetMapping("/details/{id}")
	public ResponseEntity<?> getFurnitureDetails(@PathVariable Long id) {
		// Finding ad
		Furniture furniture = furnitureService.findById(id);
		if (furniture == null) {
			return ResponseEntity.status(404).body("Δεν βρέθηκε αγγελία");
		}
		// Getting ad info through furnitureDTO
		FurnitureDTO furnitureDTO = new FurnitureDTO(furniture);
		return ResponseEntity.ok(furnitureDTO);
	}

	// Method for finding ads of a specific user
	@GetMapping("/myAds")
	public ResponseEntity<List<FurnitureDTO>> getMyAds(@RequestParam Long userId) {
		// Finding user
		User user = userService.findById(userId);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		// Getting list of furniture through furnitureService method
		List<Furniture> myAds = furnitureService.getFurnitureBySeller(user);
		List<FurnitureDTO> furnitureDTOs = new ArrayList<>();
		// For each order in orders list, creating a new DTO
		for (Furniture furniture : myAds) {
			furnitureDTOs.add(new FurnitureDTO(furniture));
		}
		return ResponseEntity.ok(furnitureDTOs);
	}

	// Method for deleting ad through id
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteFurniture(@PathVariable Long id) {
		// Finding ad
		Furniture furniture = furnitureService.findById(id);
		if (furniture == null) {
			return ResponseEntity.status(404).body("Δεν βρέθηκε αγγελία");
		}
		// Deleting the ad using furnitureService
		furnitureService.deleteById(id);
		return ResponseEntity.ok("Επιτυχής διαγραφή αγγελίας");
	}

	// Method for updating furniture ad information
	@PutMapping("/updateFurniture/{id}")
	public ResponseEntity<?> updateFurniture(@PathVariable Long id, @RequestParam(required = false) String name,
			@RequestParam(required = false) String description, @RequestParam(required = false) Double price,
			@RequestParam(required = false) String furnitureCondition,
			@RequestParam(required = false) String furnitureCategory,
			@RequestPart(required = false) MultipartFile image) throws IOException {
		// Finding ad
		Furniture furniture = furnitureService.findById(id);
		if (furniture == null) {
			return ResponseEntity.status(404).body("Δεν βρέθηκε αγγελία");
		}
		// Updating information if given from user
		if (name != null)
			furniture.setName(name);
		if (description != null)
			furniture.setDescription(description);
		if (price != null)
			furniture.setPrice(price);
		if (furnitureCondition != null)
			furniture.setFurnitureCondition(furnitureCondition);
		if (furnitureCategory != null)
			furniture.setFurnitureCategory(furnitureCategory);
		// Saving ad with image if given by user
		if (image != null && !image.isEmpty()) {
			furnitureService.saveFurniture(furniture, image);
		} else {
			// Saving ad without image if not given by user
			furnitureService.saveFurnitureWithoutImage(furniture);
		}
		return ResponseEntity.ok("Ενημερώθηκε");
	}

	@GetMapping("/filteredAds")
	public List<FurnitureDTO> getFilteredAds(@RequestParam(required = false) String category,
			@RequestParam(required = false) String condition, @RequestParam(required = false) String sortOption) {
		// Getting all furnitures
		List<Furniture> furnitures = furnitureService.getFilteredFurniture(category, condition);
		if (sortOption != null) {
			if (sortOption.equals("Τιμή Αύξουσα")) {
				furnitures.sort(Comparator.comparing(Furniture::getPrice));
			} else if (sortOption.equals("Τιμή Φθίνουσα")) {
				furnitures.sort(Comparator.comparing(Furniture::getPrice, Comparator.reverseOrder()));
			}
		}
		// Creating new DTO
		List<FurnitureDTO> furnitureDTO = new ArrayList<>();
		// For each order in orders list, creating a new DTO
		for (Furniture furniture : furnitures) {
			furnitureDTO.add(new FurnitureDTO(furniture));
		}
		return furnitureDTO;
	}

	// Getting image of furniture ad
	@GetMapping("furnitures/{id}/image")
	public ResponseEntity<byte[]> getImageByFurnitureId(@PathVariable("id") Long furnitureId) {
		// Finding furniture
		Furniture furniture = furnitureService.findById(furnitureId);
		byte[] imageFile = furniture.getImage();
		// Giving the content type of the image to ResponseEntity
		return ResponseEntity.ok().contentType(MediaType.valueOf(furniture.getImageType())).body(imageFile);
	}

}