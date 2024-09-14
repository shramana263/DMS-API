package com.wish.dms_api.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.wish.dms_api.entity.User;
import com.wish.dms_api.repository.IUserRepository;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailExistValidation implements ConstraintValidator<EmailExist,String> {

		@Autowired
		IUserRepository userRepository;
		
		@Override
		public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
			
//			return false;
		    Optional<User> user =userRepository.findByEmail(s);
		    return  user.isEmpty();
		
		}

    }
