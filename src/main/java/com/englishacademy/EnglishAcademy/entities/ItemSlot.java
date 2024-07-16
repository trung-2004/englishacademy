package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itemSlot")
@SuperBuilder
public class ItemSlot extends BaseEntity{
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "itemType", nullable = false)// 0: document, 1:question, 2:tu luan
    private Integer itemType;

    @Column(name = "startDate")
    private LocalDateTime startDate;

    @Column(name = "endDate")
    private LocalDateTime endDate;

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
