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
@Table(name = "session")
@Builder
public class Session extends BaseEntity{
    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "session")
    @JsonIgnore
    private List<TestInputSession> testInputSessions;


}
