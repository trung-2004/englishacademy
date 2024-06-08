package com.englishacademy.EnglishAcademy.dtos.item_online;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemOnlineDTO {
    private Long id;
    private String title;
    private String slug;

    private String content;

    private Integer itemType;

    private Integer orderTop;

    private String pathUrl;

    private boolean status;

    private Date lastAccessed;

    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private String createdBy;

    private String modifiedBy;
}
