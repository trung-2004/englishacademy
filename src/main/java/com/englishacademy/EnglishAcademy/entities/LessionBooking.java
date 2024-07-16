package com.englishacademy.EnglishAcademy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
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
    @Column(name = "scheduledStartTime", nullable = false)
    private LocalDateTime scheduledStartTime;
    @Column(name = "scheduledEndTime", nullable = false)
    private LocalDateTime scheduledEndTime;
    @Column(name = "actualStartTime")
    private LocalDateTime actualStartTime;
    @Column(name = "actualEndTime")
    private LocalDateTime actualEndTime;
    @Column(name = "status", nullable = false)
    private LessonBookingStatus status;
    @Column(name = "path")
    private String path;
}
