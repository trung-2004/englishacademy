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
@Table(name = "classSlot")
@Builder
public class ClassesSlot extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classesId")
    private Classes classes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slotId")
    private Slot slot;

    @Column(name = "time", nullable = false)
    private String time;

    @OneToMany(mappedBy = "classesSlot")
    @JsonIgnore
    private List<ItemSlot> itemSlots;
}
