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
@Table(name = "answerStudentOnline")
@Builder
public class AnswerStudentOnline extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionTestInputId")
    private QuestionTestOnline questionTestOnline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testOnlineStudentId")
    private TestOnlineStudent testOnlineStudent;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "isCorrect")
    private boolean isCorrect;
}
