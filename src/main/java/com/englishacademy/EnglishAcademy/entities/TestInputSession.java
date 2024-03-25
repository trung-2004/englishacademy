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
@Table(name = "testInputSession")
@Builder
public class TestInputSession extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testInputId")
    private TestInput testInput;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessionId")
    private Session session;

    @Column(name = "totalQuestion", nullable = false)
    private Integer totalQuestion;

    @Column(name = "orderTop", nullable = false)
    private Integer orderTop;

    @OneToMany(mappedBy = "testInputSession")
    @JsonIgnore
    private List<QuestionTestInput> questionTestInputs;
}
