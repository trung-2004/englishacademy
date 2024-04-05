package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itemOnline")
@Builder
public class ItemOnline extends BaseEntity{
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "itemType", nullable = false)
    private Integer itemType;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "orderTop", nullable = false)
    private Integer orderTop;

    @Column(name = "pathUrl")
    private String pathUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topicOn_id")
    private TopicOnline topicOnline;

    @OneToMany(mappedBy = "itemOnline")
    @JsonIgnore
    private List<QuestionItemOnline> questionItemOnlines;

    @OneToMany(mappedBy = "itemOnline")
    @JsonIgnore
    private List<ItemOnlineStudent> itemOnlineStudents;

}
