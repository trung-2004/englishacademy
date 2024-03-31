package com.englishacademy.EnglishAcademy.dtos.topicOnline;

import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.testOnline.TestOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.testOnline.TestOnlineResponseDTO;
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
public class TopicOnlineDetailResponse {
    private Long id;
    private String name;
    private String slug;
    private Integer orderTop;

    private List<ItemOnlineDetail> itemOnlineDetailList;

    private List<TestOnlineResponseDTO> testOnlineResponseDTOList;

    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private String createdBy;

    private String modifiedBy;

}
