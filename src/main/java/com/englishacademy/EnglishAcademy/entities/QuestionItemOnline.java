package com.englishacademy.EnglishAcademy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questionItemOnline")
@SuperBuilder
public class QuestionItemOnline extends BaseEntity{

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "answer1", nullable = false)
    private String answer1;

    @Column(name = "answer2", nullable = false)
    private String answer2;

    @Column(name = "answer3", nullable = false)
    private String answer3;

    @Column(name = "answer4", nullable = false)
    private String answer4;

    @Column(name = "answerCorrect", nullable = false)
    private String answerCorrect;

    @Column(name = "orderTop", nullable = false)
    private Integer orderTop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemOn_id")
    private ItemOnline itemOnline;

}
