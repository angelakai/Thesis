package com.example.ptyxiakh.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ptyxiakh.Repositories.OrdersRepository;
import com.example.ptyxiakh.Repositories.UserRepository;
import com.example.ptyxiakh.entities.Furniture;
import com.example.ptyxiakh.entities.Orders;
import com.example.ptyxiakh.entities.User;

@Service
public class OrdersService {
	// Connecting the repositories needed
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private UserRepository userRepository;

	// Method for finding all orders of the app
	public List<Orders> getAllOrders() {
		return ordersRepository.findAll();
	}

	// Method for finding order through id
	public Orders findById(Long id) {
		return ordersRepository.findById(id).orElse(null);
	}

	// Method for finding ad through buyer
	public List<Orders> getOrdersByBuyer(User user) {
		return ordersRepository.findByBuyer(user);
	}

	// Method for deleting order through id
	public void deleteById(Long id) {
		ordersRepository.deleteById(id);
	}
	
	// Method for getting orders through furniture
	public List<Orders> getOrdersByFurniture(Furniture furniture) {
		return ordersRepository.findByProduct(furniture);
	}

	// Method for saving order
	public Orders saveOrder(Orders order) {
		// Creating new order
		Orders newOrder = ordersRepository.save(order);
		// Getting ad of the order
		Furniture product = newOrder.getProduct();
		// Finding all users
		List<User> users = userRepository.findAll();
		// Iterating through users
		for (User user : users) {
			// If product exists in user favorites removing it
			if (user.getFavorites().remove(product)) {
				// Saving user after changes
				userRepository.save(user);
			}
		}
		return newOrder;
	}

}