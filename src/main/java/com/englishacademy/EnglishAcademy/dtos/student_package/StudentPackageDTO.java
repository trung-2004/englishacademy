package com.englishacademy.EnglishAcademy.dtos.student_package;

import com.englishacademy.EnglishAcademy.dtos.booking.LessonDay;
import com.englishacademy.EnglishAcademy.entities.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    private Double packagePrice;
    private Long studentId;
    private String studentName;
    private boolean status1;
    private Integer remainingSessions;
    private LocalDateTime purchaseDate ;
    private List<LessonDay> lessonDays;
    private BookingStatus status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
