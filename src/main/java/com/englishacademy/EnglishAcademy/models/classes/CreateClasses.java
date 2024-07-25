package com.englishacademy.EnglishAcademy.models.classes;

import lombok.Data;

import java.util.List;

@Data
public class CreateClasses {
    private Long courseOfflineId;
    private Long teacherId;
    private Long roomId;
    private List<Long> studentIds;
    private String name;
}
