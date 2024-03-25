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
@Table(name = "sessionInput")
@Builder
public class SessionInput extends BaseEntity{
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "type", nullable = false)// 0 = toeic ; 1 = ielts
    private Integer type;

    @Column(name = "orderTop", nullable = false)
    private Integer orderTop;

    @Column(name = "totalQuestion", nullable = false)
    private String totalQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testInputId")
    private TestInput testInput;

    @OneToMany(mappedBy = "sessionInput")
    @JsonIgnore
    private List<QuestionTestInput> questionTestInputs;

}
