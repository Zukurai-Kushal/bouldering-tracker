package com.fdm.bouldering_tracker.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.fdm.bouldering_tracker.model.Location;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ApiError> handleUserAlreadyExists(UserAlreadyExistsException ex, HttpServletRequest request) {
	    ApiError error = new ApiError(
	        HttpStatus.CONFLICT.value(),
	        HttpStatus.CONFLICT.getReasonPhrase(),
	        ex.getMessage(),
	        request.getRequestURI()
	    );
	    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));

        ApiError error = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            message,
            request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }
    
    @ExceptionHandler(InvalidUserRequestException.class)
    public ResponseEntity<ApiError> handleInvalidUserRequest(InvalidUserRequestException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }
    
    @ExceptionHandler(UnauthorizedClimbAccessException.class)
    public ResponseEntity<ApiError> handleUnauthorizedClimbAccess(UnauthorizedClimbAccessException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
            HttpStatus.FORBIDDEN.value(),
            HttpStatus.FORBIDDEN.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleEnumParseError(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String message = "Invalid input format.";

        if (ex.getCause() instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException formatException) {
            String targetType = formatException.getTargetType().getSimpleName();
            String invalidValue = formatException.getValue().toString();
            message = "Invalid value '" + invalidValue + "' for type " + targetType + ". Accepted values: " +
                      java.util.Arrays.toString(Location.Types.values());
        }

        ApiError error = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            message,
            request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

}