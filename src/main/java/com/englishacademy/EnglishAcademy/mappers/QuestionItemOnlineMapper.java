package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.question_item_online.QuestionItemOnlineDTO;
import com.englishacademy.EnglishAcademy.entities.QuestionItemOnline;
import org.springframework.stereotype.Component;

@Component
public class QuestionItemOnlineMapper {
    public QuestionItemOnlineDTO toQuestionItemOnlineDTO(QuestionItemOnline model){
        if (model == null) {
            return null;
        }
        QuestionItemOnlineDTO questionItemOnlineDTO = QuestionItemOnlineDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .answer1(model.getAnswer1())
                .answer2(model.getAnswer2())
                .answer3(model.getAnswer3())
                .answer4(model.getAnswer4())
                .answerCorrect(model.getAnswerCorrect())
                .orderTop(model.getOrderTop())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return questionItemOnlineDTO;
    }
}
