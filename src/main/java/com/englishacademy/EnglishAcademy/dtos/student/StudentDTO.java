package com.englishacademy.EnglishAcademy.dtos.student;

import com.englishacademy.EnglishAcademy.entities.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {
    private Long id;
    private String code;
    private String fullName;
    private String email;
    private String phone;
    private Integer status;
    private Gender gender;
    private LocalDate dayOfBirth;
    private String address;
    private Date startDate;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
