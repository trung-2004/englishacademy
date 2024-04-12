package com.englishacademy.EnglishAcademy.dtos.itemSlot;

import com.englishacademy.EnglishAcademy.dtos.answerStudentItemSlot.AnswerStudentItemSlotResponse;
import com.englishacademy.EnglishAcademy.entities.AnswerStudentItemSlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemSlotDetail {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private Integer itemType;
    private Integer orderTop;
    private String pathUrl;
    private Date startDate;
    private Date endDate;
    private List<AnswerStudentItemSlotResponse> answerStudentItemSlotResponseListList;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
