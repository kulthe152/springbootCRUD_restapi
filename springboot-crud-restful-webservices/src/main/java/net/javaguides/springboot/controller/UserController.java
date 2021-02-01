package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	// get all user
	@GetMapping
	public List<User> getAllUser(){
		return this.userRepository.findAll();
	}
	
	// get user by id
	@GetMapping("/{id}")
	public User getUserById(@PathVariable (value = "id") long userID) {
		return this.userRepository.findById(userID).orElseThrow(()->new ResourceNotFoundException("User not found with id "+userID));
	}
	
	//create user
	@PostMapping
	public User createUser(@RequestBody User user) {
		return this.userRepository.save(user);
	}
	
	//update user
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user, @PathVariable("id") long userID) {
			
		User existing = this.getUserById(userID);
		existing.setFirstName(user.getFirstName());
		existing.setLastName(user.getLastName());
		existing.setEmail(user.getEmail());
		return this.createUser(existing);
	}
	
	//delete user
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") Long userId){
		User existingUser = this.getUserById(userId);
		this.userRepository.delete(existingUser);
		return ResponseEntity.ok().build();
	}
	
	
}



















