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
@Table(name = "booking")
@Builder
public class Booking extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
    @Column(name = "bookingTime", nullable = false)
    private Date bookingTime;
    @Column(name = "duration", nullable = false)
    private Integer duration;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "booking")
    @JsonIgnore
    private List<LessionBooking> lessionBookings;
}
