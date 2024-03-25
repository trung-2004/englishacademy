package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "testInput")
@Builder
public class TestInput extends BaseEntity{
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "type", nullable = false)// 0 = toeic ; 1 = ielts
    private Integer type;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "totalQuestion", nullable = false)
    private String totalQuestion;

    @Column(name = "time", nullable = false)
    private Integer time;


    @OneToMany(mappedBy = "testInput")
    @JsonIgnore
    private List<SessionInput> sessionInputs;

}
