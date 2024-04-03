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
@Table(name = "room")
@Builder
public class Room extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<Classes> classes;
}
