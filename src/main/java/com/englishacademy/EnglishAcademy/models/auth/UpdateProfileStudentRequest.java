package com.englishacademy.EnglishAcademy.models.auth;

import com.englishacademy.EnglishAcademy.entities.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateProfileStudentRequest {
    @NotNull(message = "Date of birth cannot be null")
    private LocalDate dob;
    @NotEmpty(message = "Phone number cannot be empty")
    private String phone;
    @NotEmpty(message = "Address cannot be empty")
    private String address;
    @NotEmpty(message = "Gender cannot be empty")
    private Gender gender;
}
