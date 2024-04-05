package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courseOnline")
@Builder
public class CourseOnline extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "star", nullable = false)
    private Double star;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "trailer", nullable = false)
    private String trailer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "courseOnline")
    @JsonIgnore
    private List<CourseOnlineStudent> courseOnlineStudents;

    @OneToMany(mappedBy = "courseOnline")
    @JsonIgnore
    private List<TopicOnline> topicOnlines;
}
