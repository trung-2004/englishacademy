package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.test_offline_student.TestOfflineStudentDTO;
import com.englishacademy.EnglishAcademy.entities.TestOfflineStudent;
import org.springframework.stereotype.Component;

@Component
public class TestOfflineStudentMapper {
    public TestOfflineStudentDTO toTestOfflineStudentDTO(TestOfflineStudent testOfflineStudent) {
        if (testOfflineStudent == null) return null;
        TestOfflineStudentDTO testOfflineStudentDTO = TestOfflineStudentDTO.builder()
                .id(testOfflineStudent.getId())
                .testOfflineId(testOfflineStudent.getTestOffline().getId())
                .studentId(testOfflineStudent.getStudent().getId())
                .code(testOfflineStudent.getCode())
                .score(testOfflineStudent.getScore())
                .time(testOfflineStudent.getTime())
                .isPassed(testOfflineStudent.isPassed())
                .status(testOfflineStudent.isStatus())
                .createdBy(testOfflineStudent.getCreatedBy())
                .createdDate(testOfflineStudent.getCreatedDate())
                .modifiedDate(testOfflineStudent.getModifiedDate())
                .modifiedBy(testOfflineStudent.getModifiedBy())
                .build();
        return testOfflineStudentDTO;
    }
}
