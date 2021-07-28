package com.stuartyee.javabelt.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.stuartyee.javabelt.models.User;
import com.stuartyee.javabelt.services.UserService;

@Component
public class UserValidator implements Validator{
	
	@Autowired
	UserService uServ;
	
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    
    // 2
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            // 3
            errors.rejectValue("passwordConfirm", "Match", "password does not match SPY");
        }   
        
        if (uServ.existsByEmail(user.getEmail())) {
        	errors.rejectValue("email", "Match", "This user already exists");
        }
        
        if(user.getName().contains("<script")) {
        	errors.rejectValue("name", "Forbidden", "Don't do that!");
        }
    }

}
