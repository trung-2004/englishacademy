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
@Table(name = "questionTestOffline")
@Builder
public class QuestionTestOffline extends BaseEntity{
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "audiomp3")
    private String audiomp3;

    @Column(name = "image")
    private String image;

    @Column(name = "paragraph")
    private String paragraph;

    @Column(name = "option1")
    private String option1;

    @Column(name = "option2")
    private String option2;

    @Column(name = "option3")
    private String option3;

    @Column(name = "option4")
    private String option4;

    @Column(name = "correctanswer")
    private String correctanswer;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "part", nullable = false)
    private Integer part;

    @Column(name = "orderTop", nullable = false)
    private Integer orderTop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testOnlineSessionId")
    private TestOfflineSession testOfflineSession;

    @OneToMany(mappedBy = "questionTestOffline")
    @JsonIgnore
    private List<AnswerStudentOffline> answerStudentOfflines;
}
