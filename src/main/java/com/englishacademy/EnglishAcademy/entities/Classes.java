package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "class")
@Builder
public class Classes extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "status", nullable = false)
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherId")
    private User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Room room;

    @OneToMany(mappedBy = "classes")
    @JsonIgnore
    private List<Student> students;

    @OneToMany(mappedBy = "classes")
    @JsonIgnore
    private List<CourseOfflineStudent> courseOfflineStudents;

    @OneToMany(mappedBy = "classes")
    @JsonIgnore
    private List<ClassesSlot> classesSlots;

    @OneToMany(mappedBy = "classes")
    @JsonIgnore
    private List<ClassesTestOffline> classesTestOfflines;


}
