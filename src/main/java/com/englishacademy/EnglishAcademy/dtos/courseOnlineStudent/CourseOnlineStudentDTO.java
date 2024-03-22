package com.englishacademy.EnglishAcademy.dtos.courseOnlineStudent;


import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.student.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseOnlineStudentDTO {
    private StudentDTO student;
    private CourseOnlineDTO courseOnline;
    private Double totalPrice;

    private String paymentMethod;
}
