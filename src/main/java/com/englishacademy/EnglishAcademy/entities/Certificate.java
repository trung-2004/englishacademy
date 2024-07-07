package com.englishacademy.EnglishAcademy.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "certificate")
@SuperBuilder
public class Certificate extends BaseEntity{
    @Column(name = "course_id", nullable = false)
    private Long courseId;
    @Column(name = "userId", nullable = false)
    private Long userId;
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "fullName", nullable = false)
    private String fullName;
    @Column(name = "issued_date", nullable = false)
    private Timestamp issuedDate;
    @Column(name = "downloads_count", nullable = false)
    private Integer downloadsCount;
}
