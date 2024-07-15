package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.question_test_offline.QuestionTestOfflineDTO;
import com.englishacademy.EnglishAcademy.dtos.question_test_offline.QuestionTestOfflineDetailResult;
import com.englishacademy.EnglishAcademy.dtos.test_offline.TestOfflineDetail;
import com.englishacademy.EnglishAcademy.dtos.test_offline_student.TestOfflineStudentDTO;
import com.englishacademy.EnglishAcademy.dtos.test_session.TestOfflineSessionDetail;
import com.englishacademy.EnglishAcademy.dtos.test_session.TestOfflineSessionDetailResult;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.models.answer_student.CreateAnswerOfflineStudent;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.TestOfflineService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class TestOfflineServiceImpl implements TestOfflineService {
    private final TestOfflineRepository testOfflineRepository;
    private final StudentRepository studentRepository;
    private final TestOfflineStudentRepository testOfflineStudentRepository;
    private final QuestionTestOfflineRepository questionTestOfflineRepository;
    private final AnswerStudentOfflineRepository answerStudentOfflineRepository;
    private final FileAudioService fileAudioService;

    public TestOfflineServiceImpl(
            TestOfflineRepository testOfflineRepository,
            StudentRepository studentRepository,
            TestOfflineStudentRepository testOfflineStudentRepository,
            QuestionTestOfflineRepository questionTestOfflineRepository,
            AnswerStudentOfflineRepository answerStudentOfflineRepository,
            FileAudioService fileAudioService
    ) {
        this.testOfflineRepository = testOfflineRepository;
        this.studentRepository = studentRepository;
        this.testOfflineStudentRepository = testOfflineStudentRepository;
        this.questionTestOfflineRepository = questionTestOfflineRepository;
        this.answerStudentOfflineRepository = answerStudentOfflineRepository;
        this.fileAudioService = fileAudioService;
    }

    @Override
    public TestOfflineDetail getdetailTest(String slug, Long studentId) {
        TestOffline testOffline = testOfflineRepository.findBySlug(slug);
        if (testOffline == null) throw new AppException(ErrorCode.NOTFOUND);

        // check time
        Date now = new Timestamp(System.currentTimeMillis());
        if (now.before(testOffline.getStartDate()) || now.after(testOffline.getEndDate())) throw new AppException(ErrorCode.ITNOTTIME);

        // Tìm sinh viên theo studentId
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));
        TestOfflineStudent testOfflineStudent = testOfflineStudentRepository.findByTestOfflineAndStudent(testOffline, student);
        if (testOfflineStudent == null || testOfflineStudent.isStatus() == true) throw new AppException(ErrorCode.NOTFOUND);

        Map<Integer, TestOfflineSessionDetail> sessionDetailMap = new TreeMap<>();
        for (TestOfflineSession testOnlineSession : testOffline.getTestOfflineSessions()) {
            List<QuestionTestOfflineDTO> questionTestOfflineDTOS = new ArrayList<>();
            for (QuestionTestOffline questionTestOffline : testOnlineSession.getQuestionTestOfflines()) {
                QuestionTestOfflineDTO questionTestOfflineDTO = QuestionTestOfflineDTO.builder()
                        .id(questionTestOffline.getId())
                        .audiomp3(questionTestOffline.getAudiomp3())
                        .image(questionTestOffline.getImage())
                        .paragraph(questionTestOffline.getParagraph())
                        .title(questionTestOffline.getTitle())
                        .option1(questionTestOffline.getOption1())
                        .option2(questionTestOffline.getOption2())
                        .option3(questionTestOffline.getOption3())
                        .option4(questionTestOffline.getOption4())
                        .type(questionTestOffline.getType())
                        .part(questionTestOffline.getPart())
                        .orderTop(questionTestOffline.getOrderTop())
                        .createdBy(questionTestOffline.getCreatedBy())
                        .createdDate(questionTestOffline.getCreatedDate())
                        .modifiedBy(questionTestOffline.getModifiedBy())
                        .modifiedDate(questionTestOffline.getModifiedDate())
                        .build();
                questionTestOfflineDTOS.add(questionTestOfflineDTO);
            }

            questionTestOfflineDTOS.sort(Comparator.comparingInt(QuestionTestOfflineDTO::getOrderTop));

            TestOfflineSessionDetail testOfflineSessionDetail = TestOfflineSessionDetail.builder()
                    .id(testOnlineSession.getId())
                    .testOfflineId(testOffline.getId())
                    .sessionId(testOnlineSession.getSession().getId())
                    .sessionName(testOnlineSession.getSession().getTitle())
                    .totalQuestion(testOnlineSession.getTotalQuestion())
                    .orderTop(testOnlineSession.getOrderTop())
                    .questionTestOfflineDTOS(questionTestOfflineDTOS)
                    .createdBy(testOnlineSession.getCreatedBy())
                    .createdDate(testOnlineSession.getCreatedDate())
                    .modifiedBy(testOnlineSession.getModifiedBy())
                    .modifiedDate(testOnlineSession.getModifiedDate())
                    .build();
            sessionDetailMap.put(testOnlineSession.getOrderTop(), testOfflineSessionDetail);
        }

        TestOfflineDetail testOfflineDetail = TestOfflineDetail.builder()
                .id(testOffline.getId())
                .title(testOffline.getName())
                .slug(testOffline.getSlug())
                .totalQuestion(testOffline.getTotalQuestion())
                .retakeTestId(testOffline.getRetakeTestId())
                .pastMark(testOffline.getPastMark())
                .totalMark(testOffline.getTotalMark())
                .startDate(testOffline.getStartDate())
                .endtDate(testOffline.getEndDate())
                .testOfflineSessionDetails(new ArrayList<>(sessionDetailMap.values()))
                .createdDate(testOffline.getCreatedDate())
                .modifiedBy(testOffline.getModifiedBy())
                .createdBy(testOffline.getCreatedBy())
                .modifiedDate(testOffline.getModifiedDate())
                .build();

        return testOfflineDetail;
    }

    @Override
    public void submitTest(String slug, Long studentId, List<CreateAnswerOfflineStudent> createAnswerOfflineStudentList) {
        TestOffline testOffline = testOfflineRepository.findBySlug(slug);
        if (testOffline == null) throw new AppException(ErrorCode.NOTFOUND);

        // check time
        Date now = new Timestamp(System.currentTimeMillis());
        if (now.before(testOffline.getStartDate()) || now.after(testOffline.getEndDate())) throw new AppException(ErrorCode.ITNOTTIME);

        // Tìm sinh viên theo studentId
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));
        TestOfflineStudent testOfflineStudent = testOfflineStudentRepository.findByTestOfflineAndStudent(testOffline, student);
        if (testOfflineStudent == null || testOfflineStudent.isStatus() == true) throw new AppException(ErrorCode.NOTFOUND);

        testOfflineStudent.setTime(now);
        testOfflineStudent.setStatus(true);
        testOfflineStudentRepository.save(testOfflineStudent);

        for (CreateAnswerOfflineStudent createAnswerOfflineStudent: createAnswerOfflineStudentList) {
            QuestionTestOffline questionTestOffline = questionTestOfflineRepository.findById(createAnswerOfflineStudent.getQuestionId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));

            AnswerStudentOffline answerStudentOffline = AnswerStudentOffline.builder()
                    .testOfflineStudent(testOfflineStudent)
                    .questionTestOffline(questionTestOffline)
                    .content(createAnswerOfflineStudent.getContent())
                    .isCorrect(false)
                    .build();
            answerStudentOffline.setCreatedBy(student.getFullName());
            answerStudentOffline.setModifiedBy(student.getFullName());
            answerStudentOffline.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            answerStudentOffline.setModifiedDate(new Timestamp(System.currentTimeMillis()));

            answerStudentOfflineRepository.save(answerStudentOffline);
        }
    }

    @Override
    public List<TestOfflineSessionDetailResult> getdetailToScoreTest(String code) {
        TestOfflineStudent testOfflineStudent = testOfflineStudentRepository.findByCode(code);
        if (testOfflineStudent == null) throw new AppException(ErrorCode.NOTFOUND);

        Map<Integer, TestOfflineSessionDetailResult> sessionDetailMap = new TreeMap<>();
        for (TestOfflineSession testOnlineSession : testOfflineStudent.getTestOffline().getTestOfflineSessions()) {
            List<QuestionTestOfflineDetailResult> questionTestOfflineDetailResults = new ArrayList<>();
            for (QuestionTestOffline questionTestOffline : testOnlineSession.getQuestionTestOfflines()) {
                AnswerStudentOffline answerStudentOffline = answerStudentOfflineRepository.findByTestOfflineStudentAndQuestionTestOffline(testOfflineStudent, questionTestOffline);
                if (answerStudentOffline == null) {

                }
                QuestionTestOfflineDetailResult questionTestOfflineDetailResult = QuestionTestOfflineDetailResult.builder()
                        .id(questionTestOffline.getId())
                        .audiomp3(questionTestOffline.getAudiomp3())
                        .image(questionTestOffline.getImage())
                        .paragraph(questionTestOffline.getParagraph())
                        .title(questionTestOffline.getTitle())
                        .option1(questionTestOffline.getOption1())
                        .option2(questionTestOffline.getOption2())
                        .option3(questionTestOffline.getOption3())
                        .option4(questionTestOffline.getOption4())
                        .answerCorrect(questionTestOffline.getCorrectanswer())
                        .result(false)
                        .answerForStudent(answerStudentOffline.getContent())
                        .type(questionTestOffline.getType())
                        .part(questionTestOffline.getPart())
                        .orderTop(questionTestOffline.getOrderTop())
                        .build();
                questionTestOfflineDetailResults.add(questionTestOfflineDetailResult);
            }

            questionTestOfflineDetailResults.sort(Comparator.comparingInt(QuestionTestOfflineDetailResult::getOrderTop));

            TestOfflineSessionDetailResult testOfflineSessionDetailResult = TestOfflineSessionDetailResult.builder()
                    .id(testOnlineSession.getId())
                    .testOfflineId(testOfflineStudent.getTestOffline().getId())
                    .sessionId(testOnlineSession.getSession().getId())
                    .sessionName(testOnlineSession.getSession().getTitle())
                    .totalQuestion(testOnlineSession.getTotalQuestion())
                    .orderTop(testOnlineSession.getOrderTop())
                    .questionTestOfflineDetailResults(questionTestOfflineDetailResults)
                    .createdBy(testOnlineSession.getCreatedBy())
                    .createdDate(testOnlineSession.getCreatedDate())
                    .modifiedBy(testOnlineSession.getModifiedBy())
                    .modifiedDate(testOnlineSession.getModifiedDate())
                    .build();
            sessionDetailMap.put(testOnlineSession.getOrderTop(), testOfflineSessionDetailResult);
        }
        List<TestOfflineSessionDetailResult> testOfflineSessionDetailResults = new ArrayList<>(sessionDetailMap.values());
        return testOfflineSessionDetailResults;
    }

    @Override
    public List<TestOfflineStudentDTO> getListScore(Long classId, String slug) {
        TestOffline testOffline = testOfflineRepository.findBySlug(slug);
        if (testOffline == null) throw new AppException(ErrorCode.NOTFOUND);
//        List<TestOfflineStudent> testOfflineStudents = testOfflineStudentRepository.findByTestOfflineAndStudent();
        return List.of();
    }
}
