package com.englishacademy.EnglishAcademy.dtos.studentPackage;

import com.englishacademy.EnglishAcademy.entities.BookingStatus;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentPackageDTO {
    private Long id;
    private Long package_Id;
    private Long student_Id;
    private Integer remainingSessions;
    private Date purchaseDate ;
    private String lessonDays;
    private BookingStatus status ;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
