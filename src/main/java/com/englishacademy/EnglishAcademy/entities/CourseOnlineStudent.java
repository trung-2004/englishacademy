package com.englishacademy.EnglishAcademy.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "courseOnline_student")
public class CourseOnlineStudent extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseOnline_id")
    private CourseOnline courseOnline;

    @Column(name = "totalPrice", nullable = false)
    private Double totalPrice;

    @Column(name = "paymentMethod", nullable = false)
    private String paymentMethod;

}
