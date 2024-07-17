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
import com.englishacademy.EnglishAcademy.mappers.TestOfflineStudentMapper;
import com.englishacademy.EnglishAcademy.models.answer_student.CreateAnswerOfflineStudent;
import com.englishacademy.EnglishAcademy.models.test_offline.CreateTestOffline;
import com.englishacademy.EnglishAcademy.models.test_offline.ScoreTestOfflineStudent;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.TestOfflineService;
import com.englishacademy.EnglishAcademy.utils.JsonConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestOfflineServiceImpl implements TestOfflineService {
    private final TestOfflineRepository testOfflineRepository;
    private final StudentRepository studentRepository;
    private final ClassesRepository classesRepository;
    private final ClassesTestOfflineRepository classesTestOfflineRepository;
    private final TestOfflineStudentRepository testOfflineStudentRepository;
    private final QuestionTestOfflineRepository questionTestOfflineRepository;
    private final AnswerStudentOfflineRepository answerStudentOfflineRepository;
    private final TestOfflineStudentMapper testOfflineStudentMapper;
    private final FileAudioService fileAudioService;
    private final SubjectRepository subjectRepository;
    private final ExcelUploadService excelUploadService;

    @Override
    public TestOfflineDetail getdetailTest(String slug, Long studentId) {
        TestOffline testOffline = testOfflineRepository.findBySlug(slug);
        if (testOffline == null) throw new AppException(ErrorCode.NOTFOUND);

        // check time
        Date now = new Timestamp(System.currentTimeMillis());
        if (now.before(JsonConverterUtil.convertToDateViaInstant(testOffline.getStartDate())) || now.after(JsonConverterUtil.convertToDateViaInstant(testOffline.getEndDate()))) throw new AppException(ErrorCode.ITNOTTIME);

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
        if (now.before(JsonConverterUtil.convertToDateViaInstant(testOffline.getStartDate())) || now.after(JsonConverterUtil.convertToDateViaInstant(testOffline.getEndDate()))) throw new AppException(ErrorCode.ITNOTTIME);

        // Tìm sinh viên theo studentId
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));
        TestOfflineStudent testOfflineStudent = testOfflineStudentRepository.findByTestOfflineAndStudent(testOffline, student);
        if (testOfflineStudent == null || testOfflineStudent.isStatus() == true) throw new AppException(ErrorCode.NOTFOUND);

        testOfflineStudent.setTime(now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
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
                            .answerForStudent(null)
                            .type(questionTestOffline.getType())
                            .part(questionTestOffline.getPart())
                            .orderTop(questionTestOffline.getOrderTop())
                            .build();
                    questionTestOfflineDetailResults.add(questionTestOfflineDetailResult);
                } else {
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

        Classes classes = classesRepository.findById(classId).orElseThrow(()-> new AppException(ErrorCode.NOTFOUND));
        List<TestOfflineStudent> testOfflineStudents = new ArrayList<>();
        for (Student student: classes.getStudents()){
            TestOfflineStudent testOfflineStudent = testOfflineStudentRepository.findByTestOfflineAndStudent(testOffline, student);
            if (testOfflineStudent == null) {

            } else {
                testOfflineStudents.add(testOfflineStudent);
            }
        }
        return testOfflineStudents.stream().map(testOfflineStudentMapper::toTestOfflineStudentDTO).collect(Collectors.toList());
    }

    @Override
    public void scoreList(List<ScoreTestOfflineStudent> scoreTestOfflineStudents) {
        for (ScoreTestOfflineStudent scoreTestOfflineStudent: scoreTestOfflineStudents){
            TestOfflineStudent testOfflineStudent = testOfflineStudentRepository.findById(scoreTestOfflineStudent.getId()).orElseThrow(()->new AppException(ErrorCode.NOTFOUND));
            if (testOfflineStudent == null) {

            } else {
                testOfflineStudent.setScore(scoreTestOfflineStudent.getScore());
                testOfflineStudent.setModifiedDate(new Timestamp(System.currentTimeMillis()));
                testOfflineStudentRepository.save(testOfflineStudent);
            }
        }
    }

    @Override
    public void saveTestOffline(CreateTestOffline createTestOffline) {
        if(excelUploadService.isValidExcelFile(createTestOffline.getFile())){
            try {
                Subject subject = subjectRepository.findById(createTestOffline.getSubjectId()).orElseThrow(()->new AppException(ErrorCode.NOTFOUND));
                TestOffline offline = testOfflineRepository.findBySlug(createTestOffline.getTitle().toLowerCase().replace(" ", "-"));
                if (offline != null) throw new AppException(ErrorCode.NOTFOUND);
                Classes classes = classesRepository.findById(createTestOffline.getClassesId()).orElseThrow(()->new AppException(ErrorCode.CLASS_NOTFOUND));

                TestOffline testInput = TestOffline.builder()
                        .name(createTestOffline.getTitle())
                        .slug(createTestOffline.getTitle().toLowerCase().replace(" ", "-"))
                        .subject(subject)
                        .startDate(createTestOffline.getStartDate())
                        .endDate(createTestOffline.getEndDate())
                        .pastMark(createTestOffline.getPastMark())
                        .totalMark(createTestOffline.getTotalMark())
                        .totalQuestion(0)
                        .createdBy("Demo")
                        .createdDate(new Timestamp(System.currentTimeMillis()))
                        .modifiedBy("Demo")
                        .modifiedDate(new Timestamp(System.currentTimeMillis()))
                        .build();
                testOfflineRepository.save(testInput);

                List<QuestionTestOffline> questionTestOfflineList = excelUploadService.getCustomersDataFromExcelOffline(createTestOffline.getFile().getInputStream(), testInput);
                this.questionTestOfflineRepository.saveAll(questionTestOfflineList);
                testInput.setTotalQuestion(questionTestOfflineList.size());
                testOfflineRepository.save(testInput);

                ClassesTestOffline classesTestOffline = ClassesTestOffline.builder()
                        .testOffline(testInput)
                        .classes(classes)
                        .time("12")
                        .build();
                classesTestOfflineRepository.save(classesTestOffline);

            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        } else {
            throw new AppException(ErrorCode.NOTFOUND);
        }
    }
}
