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
@Table(name = "answerStudentItemSlot")
@Builder
public class AnswerStudentItemSlot extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemSlotId")
    private ItemSlot itemSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    private Student student;

    @Column(name = "content", nullable = false, columnDefinition = "Text")
    private String content;

    @Column(name = "star", nullable = false)
    private Integer star;

    @Column(name = "date", nullable = false)
    private LocalDateTime time;

    @Column(name = "star3Count", nullable = false)
    private Integer star3Count;
    @Column(name = "star2Count", nullable = false)
    private Integer star2Count;
    @Column(name = "star1Count", nullable = false)
    private Integer star1Count;

    @OneToMany(mappedBy = "answerStudentItemSlot")
    @JsonIgnore
    private List<PeerReview> peerReviews;
}
