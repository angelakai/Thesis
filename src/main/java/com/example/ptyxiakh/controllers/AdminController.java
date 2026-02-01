package com.example.ptyxiakh.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ptyxiakh.dto.FurnitureDTO;
import com.example.ptyxiakh.dto.OrderDTO;
import com.example.ptyxiakh.dto.UserDTO;
import com.example.ptyxiakh.entities.Furniture;
import com.example.ptyxiakh.entities.Orders;
import com.example.ptyxiakh.entities.User;
import com.example.ptyxiakh.services.FurnitureService;
import com.example.ptyxiakh.services.OrdersService;
import com.example.ptyxiakh.services.UserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:3000")
public class AdminController {

	// Connecting userService to handle user operations
	@Autowired
	private UserService userService;

	// Connecting userService to handle furniture operations
	@Autowired
	private FurnitureService furnitureService;

	// Connecting userService to handle orders operations
	@Autowired
	private OrdersService ordersService;

	// Method for getting all users of the app
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		// Getting all users
		List<User> users = userService.getAllUsers();
		// Creating new DTO list
		List<UserDTO> userDTO = new ArrayList<>();
		// For each user in users list, creating a new DTO
		for (User user : users) {
			userDTO.add(new UserDTO(user));
		}
		return ResponseEntity.ok(userDTO);
	}

	// Method for disabling user using their id
	@PutMapping("/usersDisable/{id}")
	public ResponseEntity<?> disableUser(@PathVariable Long id) {
		// Finding user through id
		User user = userService.findById(id);
		// Checks if user exists
		if (user == null) {
			return ResponseEntity.status(404).body("Δεν βρέθηκε χρήστης");
		}
		// Disabling user using userService
		userService.disableUserById(id);
		return ResponseEntity.ok("Επιτυχής απενεργοποίηση χρήστη");
	}

	// Method for enabling user using their id
	@PutMapping("/usersEnable/{id}")
	public ResponseEntity<?> enableUser(@PathVariable Long id) {
		// Finding user through id
		User user = userService.findById(id);
		// Checks if user exists
		if (user == null) {
			return ResponseEntity.status(404).body("Δεν βρέθηκε χρήστης");
		}
		// Enabling user using userService
		userService.enableUserById(id);
		return ResponseEntity.ok("Επιτυχής ενεργοποίηση χρήστη");
	}

	// Method for finding ads of specific user
	@GetMapping("/users/{userId}/furniture")
	public ResponseEntity<List<FurnitureDTO>> getUserAds(@PathVariable Long userId) {
		// Finding user through id
		User user = userService.findById(userId);
		// Checks if user exists
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		// Getting list with users ads
		List<Furniture> furnitures = furnitureService.getFurnitureBySeller(user);
		// Creating new DTO list
		List<FurnitureDTO> furnitureDTO = new ArrayList<>();
		// For each order in orders list, creating a new DTO
		for (Furniture furniture : furnitures) {
			furnitureDTO.add(new FurnitureDTO(furniture));
		}
		return ResponseEntity.ok(furnitureDTO);
	}

	// Method for finding orders of specific user
	@GetMapping("/users/{userId}/orders")
	public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable Long userId) {
		// Finding buyer through id
		User buyer = userService.findById(userId);
		// Checks if buyer exists
		if (buyer == null) {
			return ResponseEntity.badRequest().build();
		}
		// Getting list with buyer orders
		List<Orders> orders = ordersService.getOrdersByBuyer(buyer);
		// Turning orders into DTO
		List<OrderDTO> orderDTO = new ArrayList<>();
		// For each order in orders list, creating a new DTO
		for (Orders order : orders) {
			orderDTO.add(new OrderDTO(order));
		}
		return ResponseEntity.ok(orderDTO);
	}

	// Method for finding all ads of the app
	@GetMapping("/furniture")
	public ResponseEntity<List<FurnitureDTO>> getAllFurniture() {
		// Getting all furnitures
		List<Furniture> furnitures = furnitureService.getAllFurniture();
		// Creating new DTO list
		List<FurnitureDTO> furnitureDTO = new ArrayList<>();
		// For each order in orders list, creating a new DTO
		for (Furniture furniture : furnitures) {
			furnitureDTO.add(new FurnitureDTO(furniture));
		}
		return ResponseEntity.ok(furnitureDTO);
	}

	// Method for deleting furniture ad through id
	@DeleteMapping("/furniture/{id}")
	public ResponseEntity<?> deleteFurniture(@PathVariable Long id) {
		// Finding furniture through id
		Furniture furniture = furnitureService.findById(id);
		// Checks if furniture exists
		if (furniture == null) {
			return ResponseEntity.status(404).body("Δεν βρέθηκε αγγελία");
		}
		// Deleting furniture using furnitureService
		furnitureService.deleteById(id);
		return ResponseEntity.ok("Επιτυχής διαγραφή αγγελίας");
	}

	// Method for finding all orders of the app
	@GetMapping("/orders")
	public ResponseEntity<List<OrderDTO>> getAllOrders() {
		List<Orders> orders = ordersService.getAllOrders();
		List<OrderDTO> OrderDTO = new ArrayList<>();
		// For each order in orders list, creating a new DTO
		for (Orders order : orders) {
			OrderDTO.add(new OrderDTO(order));
		}
		return ResponseEntity.ok(OrderDTO);
	}

	// Method for deleting order through id
	@DeleteMapping("/orders/{id}")
	public ResponseEntity<?> deleteOrders(@PathVariable Long id) {
		// Finding order through id
		Orders orders = ordersService.findById(id);
		// Checks if order exists
		if (orders == null) {
			return ResponseEntity.status(404).body("Δεν βρέθηκε παραγγελία");
		}
		// Deleting order using ordersService
		Furniture product = orders.getProduct();
		product.setState("available");
		product.setOrder(null);
		furnitureService.saveFurnitureWithoutImage(product);
		ordersService.deleteById(id);
		return ResponseEntity.ok("Επιτυχής διαγραφή αγγελίας");
	}

}
