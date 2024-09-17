package com.wish.dms_api.exception;

import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.wish.dms_api.response.ResponseHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		List<ObjectError> allErrors= ex.getBindingResult().getAllErrors();
		Map<String, String> error= new LinkedHashMap<>();
		allErrors.forEach(err->{
			String message= err.getDefaultMessage();
			String field = (err instanceof FieldError) ? ((FieldError) err).getField() : err.getObjectName();
			error.put(field, message);
		});
		return ResponseHandler.response(
				ResponseHandler.FAILURE_CODE,
				error,
				
				ResponseHandler.FAILURE_MESSAGE+" : "+ex.getMessage()
				);
//		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(Exception ex){

        return ResponseHandler.response(
                ResponseHandler.FAILURE_CODE,
                ResponseHandler.FAILURE_MESSAGE+" : "+ex.getMessage()
        );


//          ErrorResponse error=  ErrorResponse.builder()
//                  .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                  .message("You doing operation with null value")
//                  .build();
//        return  new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);

    }
	
	
//	@ExceptionHandler(value = ExpiredJwtTokenException.class)
//	public ResponseEntity<?> handleExpiredJwt(ExpiredJwtException ex) {
////	    ApiException apiException = new ApiException(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
//		return ResponseHandler.response(
//                ResponseHandler.FAILURE_CODE,
//                ResponseHandler.FAILURE_MESSAGE+" : "+ex.getMessage()
//        );
//	}
	
//	@ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleGenericException(Exception ex) {
//        return ResponseHandler.response(
//                ResponseHandler.FAILURE_CODE,
//                ResponseHandler.FAILURE_MESSAGE+" : "+ex.getMessage()
//        );
//
////        ErrorResponse error =  ErrorResponse.builder().status(
////                HttpStatus.INTERNAL_SERVER_ERROR.value())
////                .message(ex.getMessage())
////                .build();
////        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
	
	
	@ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException (Exception ex){
        ProblemDetail errorDetail=null;
        if(ex instanceof NoResourceFoundException){
            errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Resource Not Found!");
        }
        
        if(ex instanceof BadCredentialsException){
             errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(401),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Authentication Failed");

        }
        if (ex instanceof IllegalArgumentException) {
            errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Bad Request");
        }
        if (ex instanceof MissingServletRequestParameterException) {
            errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Bad Request");
        }
        if (ex instanceof AccessDeniedException) {
            errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Not Authorized");
        }
        
        if(ex instanceof SignatureException){
            errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Jwt Signature not valid!");
        }
        if(ex instanceof ExpiredJwtException){
            errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Jwt Token Already Expired!");
        }
        if(ex instanceof MalformedJwtException) {
        	errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Malformed protected header JSON");
        }





        return errorDetail;
    }
	
	
	
}
