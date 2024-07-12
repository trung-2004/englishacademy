package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "testOnline")
@SuperBuilder
public class TestOnline extends BaseEntity{
    @Column(name = "title", nullable = false, unique = true)
    private String title;
    @Column(name = "slug", nullable = false)
    private String slug;
    @Column(name = "type", nullable = false)
    private Integer type;
    @Column(name = "pastMark", nullable = false)
    private Integer pastMark;
    @Column(name = "totalMark", nullable = false)
    private Integer totalMark;
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(name = "totalQuestion", nullable = false)
    private Integer totalQuestion;
    @Column(name = "time", nullable = false)
    private Integer time;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topicOn_id")
    private TopicOnline topicOnline;
    @OneToMany(mappedBy = "testOnline")
    @JsonIgnore
    private List<TestOnlineSession> testOnlineSessions;
}
