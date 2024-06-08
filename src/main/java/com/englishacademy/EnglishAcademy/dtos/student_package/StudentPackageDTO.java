package com.englishacademy.EnglishAcademy.dtos.student_package;

import com.englishacademy.EnglishAcademy.dtos.booking.LessonDay;
import com.englishacademy.EnglishAcademy.entities.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentPackageDTO {
    private Long id;
    private Long packageId;
    private String packageName;
    private Long studentId;
    private String studentName;
    private Integer remainingSessions;
    private Date purchaseDate ;
    private List<LessonDay> lessonDays;
    private BookingStatus status ;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
