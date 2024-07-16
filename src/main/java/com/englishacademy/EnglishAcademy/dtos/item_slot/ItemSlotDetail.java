package com.englishacademy.EnglishAcademy.dtos.item_slot;

import com.englishacademy.EnglishAcademy.dtos.answer_student_item_slot.AnswerStudentItemSlotResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<AnswerStudentItemSlotResponse> answerStudentItemSlotResponseListList;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
