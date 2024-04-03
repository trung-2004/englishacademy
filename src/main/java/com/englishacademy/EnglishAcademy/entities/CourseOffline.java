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
@Table(name = "courseOffline")
@Builder
public class CourseOffline extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "price", nullable = false)
    private Double price;

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

    @OneToMany(mappedBy = "courseOffline")
    @JsonIgnore
    private List<CourseOfflineStudent> courseOfflineStudents;

    @OneToMany(mappedBy = "courseOffline")
    @JsonIgnore
    private List<Subject> subjects;
}
