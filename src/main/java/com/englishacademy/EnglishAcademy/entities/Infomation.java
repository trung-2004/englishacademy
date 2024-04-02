package com.englishacademy.EnglishAcademy.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "infomation")
@Builder
public class Infomation extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    private String address;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "website", nullable = false)
    private String website;
}
