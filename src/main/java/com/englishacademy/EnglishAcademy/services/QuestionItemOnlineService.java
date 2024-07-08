package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.question_item_online.QuestionItemOnlineDTO;
import com.englishacademy.EnglishAcademy.models.question_item_online.CreateQuestionItemOnline;
import com.englishacademy.EnglishAcademy.models.question_item_online.EditQuestionItemOnline;

public interface QuestionItemOnlineService {
    QuestionItemOnlineDTO findById(Long id);
    QuestionItemOnlineDTO create(CreateQuestionItemOnline createQuestionItemOnline);
    QuestionItemOnlineDTO edit(EditQuestionItemOnline editQuestionItemOnline);
    void delete(Long[] ids);
}
