package com.englishacademy.EnglishAcademy.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity{
    @Column(name = "code", nullable = false, length = 12)
    private String code;

    @Column(name = "fullName", nullable = false)
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
    private Role role;
}
