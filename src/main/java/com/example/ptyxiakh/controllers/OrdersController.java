package com.example.ptyxiakh.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ptyxiakh.dto.OrderDTO;
import com.example.ptyxiakh.entities.Furniture;
import com.example.ptyxiakh.entities.Orders;
import com.example.ptyxiakh.entities.User;
import com.example.ptyxiakh.services.FurnitureService;
import com.example.ptyxiakh.services.OrdersService;
import com.example.ptyxiakh.services.UserService;

@RestController
@RequestMapping("/order")
@CrossOrigin("http://localhost:3000")
public class OrdersController {

	// Connecting userService to handle orders operations
	@Autowired
	private OrdersService ordersService;

	// Connecting userService to handle user operations
	@Autowired
	private UserService userService;

	// Connecting userService to handle furniture operations
	@Autowired
	private FurnitureService furnitureService;

	// Method for creating an order
	@PostMapping("/createOrder")
	public ResponseEntity<?> createOrder(@RequestBody Orders orders) {
		// Checking if buyer exists
		if (orders.getBuyer() == null || orders.getBuyer().getId() == null) {
			return ResponseEntity.badRequest().body("Δεν βρέθηκε id χρήστη");
		}
		// Checking if product exists
		if (orders.getProduct() == null || orders.getProduct().getId() == null) {
			return ResponseEntity.badRequest().body("Δεν βρέθηκαν στοιχεία αγγελίας");
		}
		// Finding buyer through id
		User buyer = userService.findById(orders.getBuyer().getId());
		if (buyer == null) {
			return ResponseEntity.badRequest().body("Δεν βρέθηκαν στοιχεία αγοραστή");
		}
		// Setting buyer for the order
		orders.setBuyer(buyer);
		// Setting the time the order
		orders.setDate(LocalDateTime.now());
		// Finding furniture through id
		Furniture product = furnitureService.findById(orders.getProduct().getId());
		if (product == null) {
			return ResponseEntity.badRequest().body("Δεν βρέθηκαν στοιχεία αγγελίας");
		}
		// Setting product state as unavailable and saving it
		product.setState("unavailable");
		furnitureService.saveFurnitureWithoutImage(product);
		// Setting users info for the order
		orders.setProduct(product);
		orders.setUsername(buyer.getUsername());

		// Saving order using the ordersService
		Orders savedOrder = ordersService.saveOrder(orders);
		return ResponseEntity.ok(savedOrder);
	}

	// Method for finding orders of a user
	@GetMapping("/myOrders")
	public ResponseEntity<List<OrderDTO>> getMyOrders(@RequestParam Long userId) {
		// Finding user
		User buyer = userService.findById(userId);
		if (buyer == null) {
			return ResponseEntity.badRequest().build();
		}
		// Getting orders list using ordersService
		List<Orders> orders = ordersService.getOrdersByBuyer(buyer);
		// Creating new empty list
		List<OrderDTO> orderDTOs = new ArrayList<>();
		// For each order in orders list, creating a new DTO
		for (Orders order : orders) {
			orderDTOs.add(new OrderDTO(order));
		}
		return ResponseEntity.ok(orderDTOs);
	}

	// Method for finding order from an ad
	@GetMapping("/buyer")
	public ResponseEntity<OrderDTO> getBuyerInfo(@RequestParam Long furnitureId) {
		// Finding ad
		Furniture furniture = furnitureService.findById(furnitureId);
		if (furniture == null) {
			return ResponseEntity.badRequest().build();
		}
		// Finding order through ad
		List<Orders> orders = ordersService.getOrdersByFurniture(furniture);
	    if (orders.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    // Returning order information from DTO
	    OrderDTO orderDTO = new OrderDTO(orders.get(0));
	    return ResponseEntity.ok(orderDTO);
	}

	// Method for getting furniture details from specific ad
	@GetMapping("/details/{id}")
	public ResponseEntity<?> getOrderDetails(@PathVariable Long id) {
		// Finding ad
		Orders order = ordersService.findById(id);
		if (order == null) {
			return ResponseEntity.status(404).body("Δεν βρέθηκε αγγελία");
		}
		// Getting ad info through furnitureDTO
		OrderDTO orderDTO = new OrderDTO(order);
		return ResponseEntity.ok(orderDTO);
	}

	// Method for deleting order through id
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
		// Finding order
		Orders order = ordersService.findById(id);
		if (order == null) {
			return ResponseEntity.status(404).body("Δεν βρέθηκε παραγγελία");
		}
		Furniture product = order.getProduct();

		// Setting product state as unavailable and saving it
		product.setState("available");
		product.setOrder(null);

		furnitureService.saveFurnitureWithoutImage(product);

		// Deleting the orders using ordersService
		ordersService.deleteById(id);
		return ResponseEntity.ok("Επιτυχής διαγραφή αγγελίας");
	}

}
