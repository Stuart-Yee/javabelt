package com.stuartyee.javabelt.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stuartyee.javabelt.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	public List<User> findAllUsers(); //Almost always have to find all users
	public User findUserById(Long id); //Almost always have to find user by ID
	public User findUserByEmail(String email); //Needed for de-duplication purposes

}
