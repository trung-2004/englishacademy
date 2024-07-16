package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "testOfflineStudent")
@Builder
public class TestOfflineStudent extends BaseEntity{
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "score")
    private Double score;
    @Column(name = "time")
    private LocalDateTime time;
    @Column(name = "status")
    private boolean status;
    @Column(name = "isPassed")
    private boolean isPassed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testOfflineId")
    private TestOffline testOffline;

    @OneToMany(mappedBy = "testOfflineStudent")
    @JsonIgnore
    private List<AnswerStudentOffline> answerStudentOfflines;

}
