package com.englishacademy.EnglishAcademy.dtos.answer_student_item_slot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListScore {
    private Integer star3Count;
    private Integer star2Count;
    private Integer star1Count;
}
