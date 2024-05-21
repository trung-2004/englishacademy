package com.englishacademy.EnglishAcademy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lessionBooking")
@Builder
public class LessionBooking extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;
    @Column(name = "bookingTime", nullable = false)
    private Date bookingTime;
    @Column(name = "duration", nullable = false)
    private Integer duration;
    @Column(name = "status", nullable = false)
    private Integer status;
    @Column(name = "isPaid", nullable = false)
    private boolean isPaid;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "paymentMethod")
    private String paymentMethod;

}
