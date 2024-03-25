package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.questionTestInput.QuestionTestInputDTO;
import com.englishacademy.EnglishAcademy.dtos.testInput.TestInputDTO;
import com.englishacademy.EnglishAcademy.dtos.testInput.TestInputDetail;
import com.englishacademy.EnglishAcademy.dtos.testInputSession.TestInputSessionDetail;
import com.englishacademy.EnglishAcademy.entities.QuestionTestInput;
import com.englishacademy.EnglishAcademy.entities.TestInput;
import com.englishacademy.EnglishAcademy.entities.TestInputSession;
import com.englishacademy.EnglishAcademy.mappers.TestInputMapper;
import com.englishacademy.EnglishAcademy.repositories.TestInputRepository;
import com.englishacademy.EnglishAcademy.services.ITestInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestInputService implements ITestInputService {
    @Autowired
    private TestInputRepository testInputRepository;
    @Autowired
    private TestInputMapper testInputMapper;

    @Override
    public List<TestInputDTO> findAllToiec() {
        List<TestInputDTO> testInputDTOS = testInputRepository.findAllByType(0).stream().map(testInputMapper::toTestInputDTO).collect(Collectors.toList());
        return testInputDTOS;
    }

    @Override
    public List<TestInputDTO> findAllIelts() {
        List<TestInputDTO> testInputDTOS = testInputRepository.findAllByType(1).stream().map(testInputMapper::toTestInputDTO).collect(Collectors.toList());
        return testInputDTOS;
    }

    @Override
    public TestInputDetail getdetailTest(String slug) {
        TestInput testInput = testInputRepository.findBySlug(slug);
        if (testInput == null){
            throw new RuntimeException("Not Found");
        }

        List<TestInputSessionDetail> testInputSessionDetailList = new ArrayList<>();
        for (TestInputSession testInputSession : testInput.getTestInputSessions()) {
            List<QuestionTestInputDTO> questionTestInputDTOS = new ArrayList<>();
            for (QuestionTestInput questionTestInput: testInputSession.getQuestionTestInputs()) {
                QuestionTestInputDTO questionTestInputDTO = QuestionTestInputDTO.builder()
                        .audiomp3(questionTestInput.getAudiomp3())
                        .image(questionTestInput.getImage())
                        .paragraph(questionTestInput.getParagraph())
                        .title(questionTestInput.getTitle())
                        .option1(questionTestInput.getOption1())
                        .option2(questionTestInput.getOption2())
                        .option3(questionTestInput.getOption3())
                        .option4(questionTestInput.getOption4())
                        .type(questionTestInput.getType())
                        .part(questionTestInput.getPart())
                        .orderTop(questionTestInput.getOrderTop())
                        .build();
                questionTestInputDTOS.add(questionTestInputDTO);
            }

            // sort by order
            questionTestInputDTOS.sort(Comparator.comparingInt(QuestionTestInputDTO::getOrderTop));

            TestInputSessionDetail testInputSessionDetail = TestInputSessionDetail.builder()
                    .testInputId(testInput.getId())
                    .sessionId(testInputSession.getSession().getId())
                    .sessionName(testInputSession.getSession().getTitle())
                    .totalQuestion(testInputSession.getTotalQuestion())
                    .orderTop(testInputSession.getOrderTop())
                    .questionTestInputs(questionTestInputDTOS)
                    .build();
            testInputSessionDetailList.add(testInputSessionDetail);
        }
        // sort by order
        testInputSessionDetailList.sort(Comparator.comparingInt(TestInputSessionDetail::getOrderTop));

        TestInputDetail testInputDetail = TestInputDetail.builder()
                .id(testInput.getId())
                .title(testInput.getTitle())
                .slug(testInput.getSlug())
                .type(testInput.getType())
                .totalQuestion(testInput.getTotalQuestion())
                .description(testInput.getDescription())
                .testInputSessionDetails(testInputSessionDetailList)
                .createdDate(testInput.getCreatedDate())
                .modifiedBy(testInput.getModifiedBy())
                .createdBy(testInput.getCreatedBy())
                .modifiedDate(testInput.getModifiedDate())
                .build();

        return testInputDetail;
    }
}
