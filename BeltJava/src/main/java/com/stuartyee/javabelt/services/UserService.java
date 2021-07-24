package com.stuartyee.javabelt.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.stuartyee.javabelt.models.User;
import com.stuartyee.javabelt.repositories.UserRepository;
import com.stuartyee.javabelt.validators.UserValidator;

@Service
public class UserService {
	
	final private UserRepository uRepo;
	
	public UserService(UserRepository uRepo) {
		this.uRepo = uRepo;
	}
	
	//Almost always need to find all users
	public List<User> findAllUsers(){
		return uRepo.findAll();
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
		String hashpassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashpassword);
		uRepo.save(user);
	}
	
	//Find by email
	
	public User findUserByEmail(String email) {
		if(existsByEmail(email)) {
			return uRepo.findUserByEmail(email);
		} else {
			return null;
		}
	}
	
	//check password for users logggin in
	public boolean authenticateUser(String email, String password) {
		User user = uRepo.findUserByEmail(email);
		if(user == null) {
			return false;
		} else {
			if(BCrypt.checkpw(password, user.getPassword())) {
				return true;
			} else {
				return false;
			}
		}
	}

}

	
