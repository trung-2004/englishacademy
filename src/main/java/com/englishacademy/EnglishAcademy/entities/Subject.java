package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subject")
@Builder
public class Subject extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "orderTop", nullable = false)
    private Integer orderTop;

    @Column(name = "totalSlot", nullable = false)
    private Integer totalSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseOfflineId")
    private CourseOffline courseOffline;

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    private List<Slot> slots;

}
