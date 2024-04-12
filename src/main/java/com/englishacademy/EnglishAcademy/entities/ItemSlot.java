package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itemSlot")
@Builder
public class ItemSlot extends BaseEntity{
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "itemType", nullable = false)
    private Integer itemType;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "orderTop", nullable = false)
    private Integer orderTop;

    @Column(name = "pathUrl")
    private String pathUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classesSlotId")
    private ClassesSlot classesSlot;

    @OneToMany(mappedBy = "itemSlot")
    @JsonIgnore
    private List<AnswerStudentItemSlot> answerStudentItemSlots;

}
