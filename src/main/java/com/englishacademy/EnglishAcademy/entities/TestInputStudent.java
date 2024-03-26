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
@Table(name = "testInputStudent")
@Builder
public class TestInputStudent extends BaseEntity{
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "correctReading")
    private Integer correctReading;
    @Column(name = "correctListening")
    private Integer correctListening;
    @Column(name = "correctVocabulary")
    private Integer correctVocabulary;
    @Column(name = "correctGrammar")
    private Integer correctGrammar;
    @Column(name = "score")
    private Double score;
    @Column(name = "time")
    private Double time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testInputId")
    private TestInput testInput;

    @OneToMany(mappedBy = "testInputStudent")
    @JsonIgnore
    private List<AnswerStudent> answerStudents;

}
