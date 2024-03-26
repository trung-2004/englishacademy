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
@Table(name = "answerStudent")
@Builder
public class AnswerStudent extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionTestInputId")
    private QuestionTestInput questionTestInput;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testInputStudentId")
    private TestInputStudent testInputStudent;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "isCorrect")
    private boolean isCorrect;
}
