package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "student")
public class Student extends BaseEntity{
    @Column(name = "code", nullable = false, length = 12)
    private String code;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private Integer status;

    @Column(name = "resetToken")
    private String resetToken;

    @Column(name = "resetTokenExpiry")
    private Date resetTokenExpiry;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "dayOfBirth")
    private Date dayOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "startDate")
    private Date startDate;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<CourseOnlineStudent> courseOnlineStudents;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<TestInputStudent> testInputStudents;

}
