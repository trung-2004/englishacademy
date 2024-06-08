package com.englishacademy.EnglishAcademy.dtos.course_offline;


import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseOfflineDetail {

    private Long id;
    private String name;

    private String slug;

    private String image;

    private Double price;
    private String description;

    private Integer level;

    private String language;

    private Integer status;

    private String trailer;
    private List<SubjectDTO> subjectList;

    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private String createdBy;

    private String modifiedBy;
}
