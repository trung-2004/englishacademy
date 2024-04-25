package com.englishacademy.EnglishAcademy.exceptions;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /*@ExceptionHandler(value = Exception.class)
    ResponseEntity<ResponseObject> handlingRuntimeException(RuntimeException exception){
        log.error("Exception: ", exception);
        ResponseObject apiResponse = new ResponseObject();

        apiResponse.setStatusCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(exception.getMessage());
        apiResponse.setStatus(false);

        return ResponseEntity.badRequest().body(apiResponse);
    }*/

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ResponseObject> handlingRuntimeException(RuntimeException exception){
        log.error("Exception: ", exception);
        ResponseObject apiResponse = new ResponseObject();

        apiResponse.setStatusCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(exception.getMessage());
        apiResponse.setStatus(false);

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResponseObject> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ResponseObject apiResponse = new ResponseObject();

        apiResponse.setStatusCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());


        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ResponseObject> handleAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.FORBIDDEN;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ResponseObject.builder()
                        .statusCode(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = AuthenticationException.class)
    ResponseEntity<ResponseObject> handleAuthenticationException(AuthenticationException exception){
        ErrorCode errorCode = ErrorCode.INVALIDEMAILORPASSWORD;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ResponseObject.builder()
                        .statusCode(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    /*@ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ResponseObject> handlingAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ResponseObject.builder()
                        .statusCode(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }*/

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ResponseObject> handlingValidation(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e){

        }

        ResponseObject apiResponse = new ResponseObject();

        apiResponse.setStatusCode((errorCode.getCode()));
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}