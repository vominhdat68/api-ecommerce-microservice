package com.ecommerce.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class ValidException {

    /* Fix: Handle validation exceptions explicitly to avoid default 403 Forbidden
        * The Bad Request (400) response was previously blocked by the security filter chain
        * because Spring Security treated validation failure as an unauthenticated request.
        * This handler ensures a proper 400 Bad Request with a clear message is returned.
    */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
