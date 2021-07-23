package com.stuartyee.javabelt.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stuartyee.javabelt.models.User;
import com.stuartyee.javabelt.repositories.UserRepository;
import com.stuartyee.javabelt.validators.UserValidator;

@Service
public class UserService {
	
	final private UserRepository uRepo;
	final private UserValidator uValidator;
	
	public UserService(UserRepository uRepo, UserValidator uValidator) {
		this.uRepo = uRepo;
		this.uValidator = uValidator;
	}
	
	//Almost always need to find all users
	public List<User> findAllUsers(){
		return uRepo.findAllUsers();
	}
	
	//Almost always have to find user by ID
	public User findById(Long id) {
		if (uRepo.findById(id).isPresent()) {
			return uRepo.findById(id).get();
		} else {
			return null;
		}
	}
	
	//find by email for de-duplication and return true if the email is already listed
	public boolean existsByEmail(String email) {
		if(uRepo.findUserByEmail(email) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	//Create or update
	public void saveUser(User user) {
		uRepo.save(user);
	}

}
