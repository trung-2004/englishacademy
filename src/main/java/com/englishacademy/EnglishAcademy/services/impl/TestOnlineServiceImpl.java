package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.question_test_online.QuestionTestOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.question_test_online.QuestionTestOnlineDTOResponse;
import com.englishacademy.EnglishAcademy.dtos.test_online.TestOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.test_online.TestOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.test_online.TestOnlineDetailResponse;
import com.englishacademy.EnglishAcademy.dtos.test_session.TestOnlineSessionDetail;
import com.englishacademy.EnglishAcademy.dtos.test_online_student.TestOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.dtos.test_session.TestOnlineSessionDetailResponse;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.TestOnlineMapper;
import com.englishacademy.EnglishAcademy.models.answer_student.CreateAnswerStudent;
import com.englishacademy.EnglishAcademy.models.answer_student.SubmitTest;
import com.englishacademy.EnglishAcademy.models.test_online.CreateTestOnline;
import com.englishacademy.EnglishAcademy.models.test_online.EditTestOnline;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.TestOnlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestOnlineServiceImpl implements TestOnlineService {
    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final TestOnlineRepository testOnlineRepository;
    private final StudentRepository studentRepository;
    private final CourseOnlineStudentRepository courseOnlineStudentRepository;
    private final TestOnlineStudentRepository testOnlineStudentRepository;
    private final AnswerStudentOnlineRepository answerStudentOnlineRepository;
    private final QuestionTestOnlineRepository questionTestOnlineRepository;
    private final TestOnlineMapper testOnlineMapper;
    private final ExcelUploadService excelUploadService;
    private final TopicOnlineRepository topicOnlineRepository;


    @Override
    public TestOnlineDetail getdetailTest(String slug, Long studentId) {
        TestOnline testOnline = testOnlineRepository.findBySlug(slug);
        if (testOnline == null) throw new AppException(ErrorCode.NOTFOUND);

        // Tìm sinh viên theo studentId
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        CourseOnlineStudent courseOnlineStudent = courseOnlineStudentRepository.findByCourseOnlineAndStudent(testOnline.getTopicOnline().getCourseOnline(), student);
        if (courseOnlineStudent == null) throw new AppException(ErrorCode.NOTFOUND);

        TestOnlineStudent testOnlineStudent = testOnlineStudentRepository.findByTestOnlineAndStudentAndStatus(testOnline, student, true);
        if (testOnlineStudent != null) throw new AppException(ErrorCode.NOTFOUND);

        List<TestOnlineSessionDetail> testOnlineSessionDetailList = new ArrayList<>();
        for (TestOnlineSession testOnlineSession : testOnline.getTestOnlineSessions()) {
            List<QuestionTestOnlineDTO> questionTestOnlineDTOS = new ArrayList<>();
            for (QuestionTestOnline questionTestOnline: testOnlineSession.getQuestionTestOnlines()) {
                QuestionTestOnlineDTO questionTestOnlineDTO = QuestionTestOnlineDTO.builder()
                        .id(questionTestOnline.getId())
                        .audiomp3(questionTestOnline.getAudiomp3())
                        .image(questionTestOnline.getImage())
                        .paragraph(questionTestOnline.getParagraph())
                        .title(questionTestOnline.getTitle())
                        .option1(questionTestOnline.getOption1())
                        .option2(questionTestOnline.getOption2())
                        .option3(questionTestOnline.getOption3())
                        .option4(questionTestOnline.getOption4())
                        .type(questionTestOnline.getType())
                        .part(questionTestOnline.getPart())
                        .orderTop(questionTestOnline.getOrderTop())
                        .createdBy(questionTestOnline.getCreatedBy())
                        .createdDate(questionTestOnline.getCreatedDate())
                        .modifiedBy(questionTestOnline.getModifiedBy())
                        .modifiedDate(questionTestOnline.getModifiedDate())
                        .build();
                questionTestOnlineDTOS.add(questionTestOnlineDTO);
            }

            // sort by order
            questionTestOnlineDTOS.sort(Comparator.comparingInt(QuestionTestOnlineDTO::getOrderTop));

            TestOnlineSessionDetail testInputSessionDetail = TestOnlineSessionDetail.builder()
                    .id(testOnlineSession.getId())
                    .testInputId(testOnline.getId())
                    .sessionId(testOnlineSession.getSession().getId())
                    .sessionName(testOnlineSession.getSession().getTitle())
                    .totalQuestion(testOnlineSession.getTotalQuestion())
                    .orderTop(testOnlineSession.getOrderTop())
                    .questionTestOnlineDTOS(questionTestOnlineDTOS)
                    .createdBy(testOnlineSession.getCreatedBy())
                    .createdDate(testOnlineSession.getCreatedDate())
                    .modifiedBy(testOnlineSession.getModifiedBy())
                    .modifiedDate(testOnlineSession.getModifiedDate())
                    .build();
            testOnlineSessionDetailList.add(testInputSessionDetail);
        }
        // sort by order
        testOnlineSessionDetailList.sort(Comparator.comparingInt(TestOnlineSessionDetail::getOrderTop));

        TestOnlineDetail testOnlineDetail = TestOnlineDetail.builder()
                .id(testOnline.getId())
                .title(testOnline.getTitle())
                .slug(testOnline.getSlug())
                .type(testOnline.getType())
                .totalQuestion(testOnline.getTotalQuestion())
                .time(testOnline.getTime())
                .description(testOnline.getDescription())
                .testOnlineSessionDetails(testOnlineSessionDetailList)
                .createdDate(testOnline.getCreatedDate())
                .modifiedBy(testOnline.getModifiedBy())
                .createdBy(testOnline.getCreatedBy())
                .modifiedDate(testOnline.getModifiedDate())
                .build();

        return testOnlineDetail;
    }

    @Override
    public TestOnlineDetailResponse getdetailTestByUser(String slug, Long id) {
        TestOnline testOnline = testOnlineRepository.findBySlug(slug);
        if (testOnline == null) throw new AppException(ErrorCode.NOTFOUND);

        // Tìm sinh viên theo studentId
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        CourseOnlineStudent courseOnlineStudent = courseOnlineStudentRepository.findByCourseOnlineAndStudent(testOnline.getTopicOnline().getCourseOnline(), student);
        if (courseOnlineStudent == null) throw new AppException(ErrorCode.NOTFOUND);

        TestOnlineStudent testOnlineStudent = testOnlineStudentRepository.findByTestOnlineAndStudentAndStatus(testOnline, student, true);
        if (testOnlineStudent != null) throw new AppException(ErrorCode.NOTFOUND);

        List<TestOnlineSessionDetailResponse> testOnlineSessionDetailList = new ArrayList<>();
        for (TestOnlineSession testOnlineSession : testOnline.getTestOnlineSessions()) {
            List<QuestionTestOnlineDTOResponse> questionTestOnlineDTOS = new ArrayList<>();
            for (QuestionTestOnline questionTestOnline: testOnlineSession.getQuestionTestOnlines()) {
                QuestionTestOnlineDTOResponse questionTestOnlineDTO = QuestionTestOnlineDTOResponse.builder()
                        .id(questionTestOnline.getId())
                        .audiomp3(questionTestOnline.getAudiomp3())
                        .image(questionTestOnline.getImage())
                        .paragraph(questionTestOnline.getParagraph())
                        .title(questionTestOnline.getTitle())
                        .option1(questionTestOnline.getOption1())
                        .option2(questionTestOnline.getOption2())
                        .option3(questionTestOnline.getOption3())
                        .option4(questionTestOnline.getOption4())
                        .correctAnswer(questionTestOnline.getCorrectanswer())
                        .type(questionTestOnline.getType())
                        .part(questionTestOnline.getPart())
                        .orderTop(questionTestOnline.getOrderTop())
                        .createdBy(questionTestOnline.getCreatedBy())
                        .createdDate(questionTestOnline.getCreatedDate())
                        .modifiedBy(questionTestOnline.getModifiedBy())
                        .modifiedDate(questionTestOnline.getModifiedDate())
                        .build();
                questionTestOnlineDTOS.add(questionTestOnlineDTO);
            }

            // sort by order
            questionTestOnlineDTOS.sort(Comparator.comparingInt(QuestionTestOnlineDTOResponse::getOrderTop));

            TestOnlineSessionDetailResponse testInputSessionDetail = TestOnlineSessionDetailResponse.builder()
                    .id(testOnlineSession.getId())
                    .testInputId(testOnline.getId())
                    .sessionId(testOnlineSession.getSession().getId())
                    .sessionName(testOnlineSession.getSession().getTitle())
                    .totalQuestion(testOnlineSession.getTotalQuestion())
                    .orderTop(testOnlineSession.getOrderTop())
                    .questionTestOnlineDTOS(questionTestOnlineDTOS)
                    .createdBy(testOnlineSession.getCreatedBy())
                    .createdDate(testOnlineSession.getCreatedDate())
                    .modifiedBy(testOnlineSession.getModifiedBy())
                    .modifiedDate(testOnlineSession.getModifiedDate())
                    .build();
            testOnlineSessionDetailList.add(testInputSessionDetail);
        }
        // sort by order
        testOnlineSessionDetailList.sort(Comparator.comparingInt(TestOnlineSessionDetailResponse::getOrderTop));

        TestOnlineDetailResponse testOnlineDetail = TestOnlineDetailResponse.builder()
                .id(testOnline.getId())
                .title(testOnline.getTitle())
                .slug(testOnline.getSlug())
                .type(testOnline.getType())
                .totalQuestion(testOnline.getTotalQuestion())
                .time(testOnline.getTime())
                .description(testOnline.getDescription())
                .testOnlineSessionDetails(testOnlineSessionDetailList)
                .createdDate(testOnline.getCreatedDate())
                .modifiedBy(testOnline.getModifiedBy())
                .createdBy(testOnline.getCreatedBy())
                .modifiedDate(testOnline.getModifiedDate())
                .build();

        return testOnlineDetail;
    }

    @Override
    public String submitTest(String slug, Long studentId, SubmitTest submitTest) {

        // Tìm testOnline theo slug
        TestOnline testOnline = testOnlineRepository.findBySlug(slug);
        if (testOnline == null) throw new AppException(ErrorCode.TESTINPUT_NOTFOUND);

        // Tìm sinh viên theo studentId
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));

        TestOnlineStudent testOnlineStudentExsiting = testOnlineStudentRepository.findByTestOnlineAndStudentAndStatus(testOnline, student, true);
        if (testOnlineStudentExsiting != null) throw new AppException(ErrorCode.NOTFOUND);

        // Tạo đối tượng TestOnlineStudent mới
        TestOnlineStudent testOnlineStudent = TestOnlineStudent.builder()
                .code(generateRandomString(8))
                .student(student)
                .time(submitTest.getTime())
                .testOnline(testOnline)
                .build();
        testOnlineStudentRepository.save(testOnlineStudent);

        // Lấy danh sách câu hỏi từ các phiên test
        List<QuestionTestOnline> questionTestOnlineList = testOnline.getTestOnlineSessions()
                .stream()
                .flatMap(session -> session.getQuestionTestOnlines().stream())
                .collect(Collectors.toList());

        // Lưu câu trả lời của sinh viên
        List<AnswerStudentOnline> answerStudentOnlineList = new ArrayList<>();
        for (CreateAnswerStudent createAnswerStudent: submitTest.getCreateAnswerStudentList()) {
            QuestionTestOnline questionTestOnline = questionTestOnlineRepository.findById(createAnswerStudent.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question Not Found"));

            AnswerStudentOnline answerStudentOnline = AnswerStudentOnline.builder()
                    .testOnlineStudent(testOnlineStudent)
                    .questionTestOnline(questionTestOnline)
                    .content(createAnswerStudent.getContent())
                    .build();

            // Lưu câu trả lời của sinh viên
            answerStudentOnlineRepository.save(answerStudentOnline);
            answerStudentOnlineList.add(answerStudentOnline);
        }

        double totalScore = 0;
        int correctReading = 0;
        int correctListening = 0;
        int correctVocabulary = 0;
        int correctGrammar = 0;

        // Tính toán score dựa trên loại bài kiểm tra và số câu hỏi
        double score;
        if (testOnline.getType() == 0) {
            score = 100.0 / testOnline.getTotalQuestion();
        } else if (testOnline.getType() == 1) {
            score = 100.0 / testOnline.getTotalQuestion();
        } else {
            throw new IllegalArgumentException("Invalid test type: " + testOnline.getType());
        }

        for (QuestionTestOnline questionTestOnline: questionTestOnlineList) {
            AnswerStudentOnline answerStudentOnline = findAnswerForQuestion(answerStudentOnlineList, questionTestOnline);
            if (answerStudentOnline != null) {
                // So sánh câu trả lời của sinh viên với đáp án chính xác của câu hỏi
                if (answerStudentOnline.getContent().trim().equals(questionTestOnline.getCorrectanswer().trim())) {
                    // Nếu câu trả lời đúng, tăng điểm lên
                    totalScore+=score;
                    answerStudentOnline.setCorrect(true);
                    if (questionTestOnline.getTestOnlineSession().getSession().getId() == 1) {
                        correctReading ++;
                    } else if (questionTestOnline.getTestOnlineSession().getSession().getId() == 2) {
                        correctListening++;
                    } else if (questionTestOnline.getTestOnlineSession().getSession().getId() == 3) {
                        correctVocabulary++;
                    } else if (questionTestOnline.getTestOnlineSession().getSession().getId() == 4) {
                        correctGrammar++;
                    }
                } else {
                    answerStudentOnline.setCorrect(false);
                }
                answerStudentOnlineRepository.save(answerStudentOnline);
            }
        }

        for (TestOnlineSession testOnlineSession: testOnline.getTestOnlineSessions()) {
            if (testOnlineSession.getSession().getId() == 1) {
                testOnlineStudent.setCorrectReading(correctReading);
            } else if (testOnlineSession.getSession().getId() == 2) {
                testOnlineStudent.setCorrectListening(correctListening);
            } else if (testOnlineSession.getSession().getId() == 3) {
                testOnlineStudent.setCorrectVocabulary(correctVocabulary);
            } else if (testOnlineSession.getSession().getId() == 4) {
                testOnlineStudent.setCorrectGrammar(correctGrammar);
            }

        }
        totalScore = roundToNearestHalf(totalScore);
        testOnlineStudent.setScore(totalScore);
        if (totalScore >= testOnline.getPastMark()){
            testOnlineStudent.setStatus(true);
        } else {
            testOnlineStudent.setStatus(false);
        }


        testOnlineStudent.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        testOnlineStudent.setCreatedBy("Demo");
        testOnlineStudent.setModifiedBy("Demo");
        testOnlineStudent.setModifiedDate(new Timestamp(System.currentTimeMillis()));

        testOnlineStudentRepository.save(testOnlineStudent);

        return testOnlineStudent.getCode();
    }


    @Override
    public TestOnlineStudentDTO getresultTest(String code) {
        TestOnlineStudent testOnlineStudent = testOnlineStudentRepository.findByCode(code);
        if (testOnlineStudent == null) throw  new AppException(ErrorCode.NOTFOUND);

        TestOnlineStudentDTO testOnlineStudentDTO = TestOnlineStudentDTO.builder()
                .id(testOnlineStudent.getId())
                .code(testOnlineStudent.getCode())
                .correctReading(testOnlineStudent.getCorrectReading())
                .correctGrammar(testOnlineStudent.getCorrectGrammar())
                .correctListening(testOnlineStudent.getCorrectListening())
                .correctVocabulary(testOnlineStudent.getCorrectVocabulary())
                .score(testOnlineStudent.getScore())
                .time(testOnlineStudent.getTime())
                .status(testOnlineStudent.isStatus())
                .createdBy(testOnlineStudent.getCreatedBy())
                .createdDate(testOnlineStudent.getCreatedDate())
                .modifiedBy(testOnlineStudent.getModifiedBy())
                .modifiedDate(testOnlineStudent.getModifiedDate())
                .build();

        for (TestOnlineSession testOnlineSession: testOnlineStudent.getTestOnline().getTestOnlineSessions()) {
            if (testOnlineSession.getSession().getId() == 1){
                testOnlineStudentDTO.setTotalQuestionReading(testOnlineSession.getTotalQuestion());
            } else if (testOnlineSession.getSession().getId() == 2) {
                testOnlineStudentDTO.setTotalQuestionListening(testOnlineSession.getTotalQuestion());
            } else if (testOnlineSession.getSession().getId() == 3) {
                testOnlineStudentDTO.setTotalQuestionVocabulary(testOnlineSession.getTotalQuestion());
            } else if (testOnlineSession.getSession().getId() == 4) {
                testOnlineStudentDTO.setTotalQuestionGrammar(testOnlineSession.getTotalQuestion());
            }
        }

        return testOnlineStudentDTO;
    }

    @Override
    public void saveTestOnline(CreateTestOnline createTestOnline) {
        if(excelUploadService.isValidExcelFile(createTestOnline.getFile())){
            try {
                TopicOnline topicOnline = topicOnlineRepository.findById(createTestOnline.getTopicId()).orElseThrow(()->new AppException(ErrorCode.NOTFOUND));
                TestOnline online = testOnlineRepository.findBySlug(createTestOnline.getTitle().toLowerCase().replace(" ", "-"));
                if (online != null) throw new AppException(ErrorCode.NOTFOUND);

                TestOnline testInput = TestOnline.builder()
                        .title(createTestOnline.getTitle())
                        .slug(createTestOnline.getTitle().toLowerCase().replace(" ", "-"))
                        .description(createTestOnline.getDescription())
                        .type(0)
                        .topicOnline(topicOnline)
                        .time(createTestOnline.getTime())
                        .pastMark(createTestOnline.getPastMark())
                        .totalMark(createTestOnline.getTotalMark())
                        .totalQuestion(0)
                        .createdBy("Demo")
                        .createdDate(new Timestamp(System.currentTimeMillis()))
                        .modifiedBy("Demo")
                        .modifiedDate(new Timestamp(System.currentTimeMillis()))
                        .build();
                testOnlineRepository.save(testInput);

                List<QuestionTestOnline> questionTestOnlineList = excelUploadService.getCustomersDataFromExcelOnline(createTestOnline.getFile().getInputStream(), testInput);
                this.questionTestOnlineRepository.saveAll(questionTestOnlineList);
                testInput.setTotalQuestion(questionTestOnlineList.size());
                testOnlineRepository.save(testInput);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        } else {
            throw new AppException(ErrorCode.NOTFOUND);
        }
    }

    @Override
    public TestOnlineDTO edit(EditTestOnline editTestOnline) {
        TestOnline testOnline = testOnlineRepository.findById(editTestOnline.getId()).orElseThrow(()->new AppException(ErrorCode.NOTFOUND));
        TestOnline testOnline1 = testOnlineRepository.findBySlug(editTestOnline.getTitle().toLowerCase().replace(" ", "-"));
        if (testOnline1 != null && !testOnline1.getSlug().equals(testOnline.getSlug())) throw  new AppException(ErrorCode.NOTFOUND);
        testOnline.setTitle(editTestOnline.getTitle());
        testOnline.setSlug(editTestOnline.getTitle().toLowerCase().replace(" ", "-"));
        testOnline.setDescription(editTestOnline.getDescription());
        testOnline.setTime(editTestOnline.getTime());
        testOnline.setPastMark(editTestOnline.getPastMark());
        testOnline.setTotalMark(editTestOnline.getTotalMark());
        testOnline.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        testOnlineRepository.save(testOnline);
        return testOnlineMapper.toTestOnlineDTO(testOnline);
    }

    @Override
    public void delete(List<Long> ids) {
        testOnlineRepository.deleteAllById(ids);
    }

    @Override
    public TestOnlineDTO getBySlug(String slug) {
        TestOnline testOnline = testOnlineRepository.findBySlug(slug);
        if (testOnline == null) throw new AppException(ErrorCode.NOTFOUND);
        return testOnlineMapper.toTestOnlineDTO(testOnline);
    }


    public static double roundToNearestHalf(double number) {
        // Lấy phần thập phân của số
        double fractionalPart = number - (int) number;

        // Làm tròn phần thập phân
        if (fractionalPart < 0.25) {
            return Math.floor(number); // Làm tròn xuống
        } else if (fractionalPart >= 0.75) {
            return Math.ceil(number); // Làm tròn lên
        } else {
            return Math.floor(number) + 0.5; // Làm tròn về 0.5
        }
    }

    private AnswerStudentOnline findAnswerForQuestion(List<AnswerStudentOnline> studentAnswers, QuestionTestOnline questionTestOnline) {
        for (AnswerStudentOnline answer : studentAnswers) {
            if (answer.getQuestionTestOnline().equals(questionTestOnline)) {
                return answer;
            }
        }
        return null;
    }

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            sb.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
