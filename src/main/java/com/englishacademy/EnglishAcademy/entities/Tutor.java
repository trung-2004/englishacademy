package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tutor")
@Builder
public class Tutor extends BaseEntity{
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "level", nullable = false)
    private Integer level;
    @Column(name = "avatar", nullable = false)
    private String avatar;
    @Column(name = "address")
    private String address;
    @Column(name = "cetificate", nullable = false, columnDefinition = "TEXT")
    private String cetificate;
    @Column(name = "experience", nullable = false, columnDefinition = "TEXT")
    private String experience;
    @Column(name = "teachingSubject", nullable = false)
    private String teachingSubject;
    @Column(name = "hourlyRate", nullable = false)
    private Integer hourlyRate;
    @Column(name = "status", nullable = false)
    private boolean status;

    @OneToMany(mappedBy = "tutor")
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "tutor")
    @JsonIgnore
    private List<Availability> availabilities;
}
