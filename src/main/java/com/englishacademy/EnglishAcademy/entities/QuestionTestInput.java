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
@Table(name = "questionTestInput")
@Builder
public class QuestionTestInput extends BaseEntity{
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "audiomp3", nullable = false)
    private String audiomp3;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "paragraph", nullable = false)
    private String paragraph;

    @Column(name = "option1", nullable = false)
    private String option1;

    @Column(name = "option2", nullable = false)
    private String option2;

    @Column(name = "option3", nullable = false)
    private String option3;

    @Column(name = "option4", nullable = false)
    private String option4;

    @Column(name = "correctanswer", nullable = false)
    private String correctanswer;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "part", nullable = false)
    private Integer part;

    @Column(name = "orderTop", nullable = false)
    private Integer orderTop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessionInputId")
    private SessionInput sessionInput;

}
