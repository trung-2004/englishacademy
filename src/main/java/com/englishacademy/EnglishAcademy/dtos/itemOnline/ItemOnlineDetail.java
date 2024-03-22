package com.englishacademy.EnglishAcademy.dtos.itemOnline;

import com.englishacademy.EnglishAcademy.dtos.questionItemOnline.QuestionItemOnlineDTO;
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
public class ItemOnlineDetail {
    private Long id;
    private String title;

    private String content;

    private Integer itemType;

    private Integer orderTop;

    private String pathUrl;

    private List<QuestionItemOnlineDTO> questionItemOnlineDTOList;

    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private String createdBy;

    private String modifiedBy;
}
