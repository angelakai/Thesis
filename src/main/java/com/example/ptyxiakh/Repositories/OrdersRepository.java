package com.example.ptyxiakh.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ptyxiakh.entities.Furniture;
import com.example.ptyxiakh.entities.Orders;
import com.example.ptyxiakh.entities.User;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
	// Finding orders list for specific buyer
	List<Orders> findByBuyer(@Param("buyer") User buyer);
	
	// Finding orders through product
	List<Orders> findByProduct(Furniture furniture);
	
}
