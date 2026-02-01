package com.example.ptyxiakh.Repositories;

import java.util.Optional;

import com.example.ptyxiakh.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// Finding specific user through username
	Optional<User> findByUsername(String username);

	// Finding specific user through email
	Optional<User> findByEmail(String email);

	// Finding specific user through role
	Optional<User> findByRole(String role);
	
	
}
