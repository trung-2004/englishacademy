package com.englishacademy.EnglishAcademy.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "potentialCustomer")
@SuperBuilder
public class PotentialCustomer extends BaseEntity{
    @Column(name = "fullname", nullable = false)
    private String fullName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "status")
    private Integer status;
}
