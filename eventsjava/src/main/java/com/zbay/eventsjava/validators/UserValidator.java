package com.zbay.eventsjava.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.zbay.eventsjava.models.User;
import com.zbay.eventsjava.services.UserService;

@Component
public class UserValidator implements Validator {
	
	private UserService userService;
	
	public UserValidator(UserService userService) {
		this.userService = userService;
	}
	
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    	    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    
    @Override
    public void validate(Object object, Errors errors) { // check that email is valid and that password matches confirmation
        User user = (User) object;
        Matcher validEmail = VALID_EMAIL_ADDRESS_REGEX .matcher(user.getEmail());
        
        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirmation", "Match");
        }     
        
        if (!validEmail.find()) {
        	errors.rejectValue("email", "EmailFormat");
        }
        
        if(this.userService.findByEmail(user.getEmail()).size() > 0) {
        	errors.rejectValue("email", "TakenEmail");
        }
    }
}