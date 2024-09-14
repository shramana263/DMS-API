package com.wish.dms_api.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EmailExistValidation.class})
public @interface EmailExist {

    public String message() default "email already exist";
    public Class<?>[] groups() default{};
    public  Class<? extends Payload >[] payload() default {};

}
