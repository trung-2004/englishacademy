package com.englishacademy.EnglishAcademy.models.courseOnlineStudent;

    import lombok.Data;

@Data
public class CreateCourseOnlineStudent {
    private Long studentId;

    private Long courseOnlineId;

    private String paymentMethod;
}
