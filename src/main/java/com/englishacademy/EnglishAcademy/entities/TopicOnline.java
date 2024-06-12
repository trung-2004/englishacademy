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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "topicOnline")
@SuperBuilder
public class TopicOnline extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "orderTop", nullable = false)
    private Integer orderTop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseOnline_id")
    private CourseOnline courseOnline;

    @OneToMany(mappedBy = "topicOnline")
    @JsonIgnore
    private List<ItemOnline> itemOnlines;

    @OneToMany(mappedBy = "topicOnline")
    @JsonIgnore
    private List<TestOnline> testOnlines;
}
