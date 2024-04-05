package com.englishacademy.EnglishAcademy.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    // Course
    COURSE_NOTFOUND(404, "Course Not Found", HttpStatus.NOT_FOUND),
    COURSE_EXISTED(400, "User existed", HttpStatus.BAD_REQUEST),
    COURSE_PURCHASED(400, "This course has been purchased", HttpStatus.BAD_REQUEST),
    COURSE_NOTPURCHASED(400, "This course has't been purchased", HttpStatus.BAD_REQUEST),
    // Item online

    ITEMONLINE_NOTFOUND(404, "Item Online Not Found", HttpStatus.NOT_FOUND),

    // Class
    CLASS_NOTFOUND(404, "Students do not have classes", HttpStatus.NOT_FOUND),

    // Test
    TESTINPUT_NOTFOUND(404, "Test Input Not Found", HttpStatus.NOT_FOUND),

    NOTFOUND(404, "Not Found", HttpStatus.NOT_FOUND),
    STUDENT_NOTFOUND(404, "Student Not Found", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    ;
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
