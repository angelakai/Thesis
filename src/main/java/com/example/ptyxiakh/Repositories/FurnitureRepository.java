package com.example.ptyxiakh.Repositories;

import java.util.List;

import com.example.ptyxiakh.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ptyxiakh.entities.Furniture;

@Repository
public interface FurnitureRepository extends JpaRepository<Furniture, Long> {
	// Finding furniture list for specific seller
	List<Furniture> findBySeller(User seller);

	// Finding furniture list by state, category, condition and sellers accountStatus
	List<Furniture> findByStateAndFurnitureCategoryAndFurnitureConditionAndSellerAccountStatus(String state,
			String category, String condition, String accountStatus);
	// Finding furniture list by state, category and sellers accountStatus
	List<Furniture> findByStateAndFurnitureCategoryAndSellerAccountStatus(String state, String category,
			String accountStatus);
	// Finding furniture by state, condition and sellers accountStatus
	List<Furniture> findByStateAndFurnitureConditionAndSellerAccountStatus(String state, String condition,
			String accountStatus);
	// Finding furniture by state and sellers accountStatus
	List<Furniture> findByStateAndSellerAccountStatus(String state, String accountStatus);

}
