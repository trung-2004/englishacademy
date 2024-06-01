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
@Table(name = "subscription")
@Builder
public class Subscription extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
    @Column(name = "startTime", nullable = false)
    private Date startTime;
    @Column(name = "endTime", nullable = false)
    private Date endTime;
    @Column(name = "nextPaymentDate ", nullable = false)
    private Date nextPaymentDate ;
    @Column(name = "lessonDays", nullable = false, columnDefinition = "TEXT")
    private String lessonDays;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "status", nullable = false)
    private BookingStatus status ;
    @OneToMany(mappedBy = "subscription")
    @JsonIgnore
    private List<Payment> payments;
}
