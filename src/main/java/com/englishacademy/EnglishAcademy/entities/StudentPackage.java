package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "studentPackage")
@Builder
public class StudentPackage extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packages_id")
    private Packages packages;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
    @Column(name = "remainingSessions", nullable = false)
    private Integer remainingSessions;
    @Column(name = "purchaseDate", nullable = false)
    private Date purchaseDate ;
    @Column(name = "lessonDays", nullable = false, columnDefinition = "TEXT")
    private String lessonDays;
    @Column(name = "status", nullable = false)
    private BookingStatus status ;

    @OneToMany(mappedBy = "studentPackage")
    @JsonIgnore
    private List<Payment> payments;

}
