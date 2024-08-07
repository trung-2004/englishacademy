package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "testOffline")
@SuperBuilder
public class TestOffline extends BaseEntity{
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "pastMark", nullable = false)
    private Integer pastMark;

    @Column(name = "totalMark", nullable = false)
    private Integer totalMark;

    @Column(name = "startDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "retakeTestId")
    private Integer retakeTestId;

    @Column(name = "totalQuestion", nullable = false)
    private Integer totalQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(mappedBy = "testOffline")
    @JsonIgnore
    private List<TestOfflineSession> testOfflineSessions;

    @OneToMany(mappedBy = "testOffline")
    @JsonIgnore
    private List<ClassesTestOffline> classesTestOfflines;
}
