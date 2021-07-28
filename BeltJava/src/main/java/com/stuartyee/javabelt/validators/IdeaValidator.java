package com.stuartyee.javabelt.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.stuartyee.javabelt.models.Idea;
import com.stuartyee.javabelt.services.IdeaService;

@Component
public class IdeaValidator implements Validator{
	
	@Autowired
	IdeaService iServ;
	
    @Override
    public boolean supports(Class<?> clazz) {
        return Idea.class.equals(clazz);
    }
    
    // 2
    @Override
    public void validate(Object target, Errors errors) {
        Idea idea = (Idea) target;
        
        if (idea.getName().contains("<script")) {
            // 3
            errors.rejectValue("name", "Forbidden", "Don't do that!");
        }   
        

    }

}
