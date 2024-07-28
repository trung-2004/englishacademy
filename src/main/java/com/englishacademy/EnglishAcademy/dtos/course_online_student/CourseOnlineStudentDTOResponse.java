package com.englishacademy.EnglishAcademy.dtos.course_online_student;

import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.student.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseOnlineStudentDTOResponse {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long courseOnlineId;
    private String courseOnlineName;
    private Double totalPrice;
    private String paymentMethod;
    private Timestamp createddate;
}
