package com.englishacademy.EnglishAcademy.dtos.subject;

import com.englishacademy.EnglishAcademy.dtos.slot.SlotResponseDetail;
import com.englishacademy.EnglishAcademy.dtos.testOffline.TestOfflineResponse;
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
public class SubjectDetail {
    private Long id;
    private String name;
    private String slug;
    private Integer orderTop;
    private Integer totalSlot;
    private List<SlotResponseDetail> slotResponseDetailList;
    private List<TestOfflineResponse> testOfflineResponseList;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
