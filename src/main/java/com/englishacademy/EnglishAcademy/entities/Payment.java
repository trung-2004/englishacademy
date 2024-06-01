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
@Table(name = "payment")
@Builder
public class Payment extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_packages_id")
    private StudentPackage studentPackage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;
    @Column(name = "paymentMethod", nullable = false)
    private String paymentMethod;
    @Column(name = "isPaid", nullable = false)
    private boolean isPaid;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "paymentDate", nullable = false)
    private Date paymentDate;
    @OneToMany(mappedBy = "payment")
    @JsonIgnore
    private List<Booking> bookings;
}
