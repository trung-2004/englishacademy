package com.englishacademy.EnglishAcademy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answerStudentOffline")
@Builder
public class AnswerStudentOffline extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionTestOfflineId")
    private QuestionTestOffline questionTestOffline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testOfflineStudentId")
    private TestOfflineStudent testOfflineStudent;

    @Column(name = "content")
    private String content;

    @Column(name = "isCorrect")
    private boolean isCorrect;
}
