package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.question_item_online.QuestionItemOnlineDTO;
import com.englishacademy.EnglishAcademy.entities.ItemOnline;
import com.englishacademy.EnglishAcademy.entities.QuestionItemOnline;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.QuestionItemOnlineMapper;
import com.englishacademy.EnglishAcademy.models.question_item_online.CreateQuestionItemOnline;
import com.englishacademy.EnglishAcademy.models.question_item_online.EditQuestionItemOnline;
import com.englishacademy.EnglishAcademy.repositories.ItemOnlineRepository;
import com.englishacademy.EnglishAcademy.repositories.QuestionItemOnlineRepository;
import com.englishacademy.EnglishAcademy.services.QuestionItemOnlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionItemOnlineServiceImpl implements QuestionItemOnlineService {
    private final QuestionItemOnlineRepository questionItemOnlineRepository;
    private final QuestionItemOnlineMapper questionItemOnlineMapper;
    private final ItemOnlineRepository itemOnlineRepository;

    @Override
    public QuestionItemOnlineDTO findById(Long id) {
        QuestionItemOnline questionItemOnline = questionItemOnlineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        return questionItemOnlineMapper.toQuestionItemOnlineDTO(questionItemOnline);
    }

    @Override
    public QuestionItemOnlineDTO create(CreateQuestionItemOnline createQuestionItemOnline) {
        ItemOnline itemOnline = itemOnlineRepository.findById(createQuestionItemOnline.getItemOnlineId())
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        QuestionItemOnline questionItemOnline = QuestionItemOnline.builder()
                .title(createQuestionItemOnline.getTitle())
                .answer1(createQuestionItemOnline.getAnswer1())
                .answer2(createQuestionItemOnline.getAnswer2())
                .answer3(createQuestionItemOnline.getAnswer3())
                .answer4(createQuestionItemOnline.getAnswer4())
                .answerCorrect(createQuestionItemOnline.getAnswerCorrect())
                .orderTop(createQuestionItemOnline.getOrderTop())
                .itemOnline(itemOnline)
                .createdBy("Demo")
                .modifiedBy("Demo")
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();
        questionItemOnlineRepository.save(questionItemOnline);
        return questionItemOnlineMapper.toQuestionItemOnlineDTO(questionItemOnline);
    }

    @Override
    public QuestionItemOnlineDTO edit(EditQuestionItemOnline editQuestionItemOnline) {
        ItemOnline itemOnline = itemOnlineRepository.findById(editQuestionItemOnline.getItemOnlineId())
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        QuestionItemOnline questionItemOnline = questionItemOnlineRepository.findById(editQuestionItemOnline.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        questionItemOnline.setTitle(editQuestionItemOnline.getTitle());
        questionItemOnline.setAnswer1(editQuestionItemOnline.getAnswer1());
        questionItemOnline.setAnswer2(editQuestionItemOnline.getAnswer2());
        questionItemOnline.setAnswer3(editQuestionItemOnline.getAnswer3());
        questionItemOnline.setAnswer4(editQuestionItemOnline.getAnswer4());
        questionItemOnline.setOrderTop(editQuestionItemOnline.getOrderTop());
        questionItemOnline.setItemOnline(itemOnline);
        questionItemOnline.setAnswerCorrect(editQuestionItemOnline.getAnswerCorrect());
        questionItemOnline.setModifiedBy("Demo");
        questionItemOnline.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        questionItemOnlineRepository.save(questionItemOnline);
        return questionItemOnlineMapper.toQuestionItemOnlineDTO(questionItemOnline);
    }

    @Override
    public void delete(Long[] ids) {
        questionItemOnlineRepository.deleteAllById(List.of(ids));
    }
}
