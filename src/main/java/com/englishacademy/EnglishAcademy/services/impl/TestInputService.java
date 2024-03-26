package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.questionTestInput.QuestionTestInputDTO;
import com.englishacademy.EnglishAcademy.dtos.questionTestInput.QuestionTestInputDetailResult;
import com.englishacademy.EnglishAcademy.dtos.testInput.TestInputDTO;
import com.englishacademy.EnglishAcademy.dtos.testInput.TestInputDetail;
import com.englishacademy.EnglishAcademy.dtos.testInputSession.TestInputSessionDetail;
import com.englishacademy.EnglishAcademy.dtos.testInputStudent.TestInputStudentDTO;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.mappers.TestInputMapper;
import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerStudent;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.ITestInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TestInputService implements ITestInputService {
    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    @Autowired
    private TestInputRepository testInputRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestInputStudentRepository testInputStudentRepository;
    @Autowired
    private AnswerStudentRepository answerStudentRepository;
    @Autowired
    private QuestionTestInputRepository questionTestInputRepository;
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
                        .id(questionTestInput.getId())
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
                        .createdBy(questionTestInput.getCreatedBy())
                        .createdDate(questionTestInput.getCreatedDate())
                        .modifiedBy(questionTestInput.getModifiedBy())
                        .modifiedDate(questionTestInput.getModifiedDate())
                        .build();
                questionTestInputDTOS.add(questionTestInputDTO);
            }

            // sort by order
            questionTestInputDTOS.sort(Comparator.comparingInt(QuestionTestInputDTO::getOrderTop));

            TestInputSessionDetail testInputSessionDetail = TestInputSessionDetail.builder()
                    .id(testInputSession.getId())
                    .testInputId(testInput.getId())
                    .sessionId(testInputSession.getSession().getId())
                    .sessionName(testInputSession.getSession().getTitle())
                    .totalQuestion(testInputSession.getTotalQuestion())
                    .orderTop(testInputSession.getOrderTop())
                    .questionTestInputs(questionTestInputDTOS)
                    .createdBy(testInputSession.getCreatedBy())
                    .createdDate(testInputSession.getCreatedDate())
                    .modifiedBy(testInputSession.getModifiedBy())
                    .modifiedDate(testInputSession.getModifiedDate())
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

    @Override
    public void submitTest(String slug, Long studentId, List<CreateAnswerStudent> answersForStudents) {

        // Tìm testInput theo slug
        TestInput testInput = testInputRepository.findBySlug(slug);
        if (testInput == null){
            throw new RuntimeException("Test Input Not Found");
        }

        // Tìm sinh viên theo studentId
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        // Tạo đối tượng TestInputStudent mới
        TestInputStudent testInputStudent = TestInputStudent.builder()
                .code(generateRandomString(8))
                .student(student)
                .time(30.0)
                .testInput(testInput)
                .build();
        testInputStudentRepository.save(testInputStudent);

        // Lấy danh sách câu hỏi từ các phiên test
        List<QuestionTestInput> questionTestInputList = testInput.getTestInputSessions()
                .stream()
                .flatMap(session -> session.getQuestionTestInputs().stream())
                .collect(Collectors.toList());

        // Lưu câu trả lời của sinh viên
        List<AnswerStudent> answerStudentList = new ArrayList<>();
        for (CreateAnswerStudent createAnswerStudent: answersForStudents) {
            QuestionTestInput questionTestInput = questionTestInputRepository.findById(createAnswerStudent.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question Not Found"));

            AnswerStudent answerStudent = AnswerStudent.builder()
                    .testInputStudent(testInputStudent)
                    .questionTestInput(questionTestInput)
                    .content(createAnswerStudent.getContent())
                    .build();

            // Lưu câu trả lời của sinh viên
            answerStudentRepository.save(answerStudent);
            answerStudentList.add(answerStudent);
        }

        double totalScore = 0;
        int correctReading = 0;
        int correctListening = 0;
        int correctVocabulary = 0;
        int correctGrammar = 0;
        double score = 0;
        if (testInput.getType() == 0){
            score = 990/(testInput.getTotalQuestion());
        } else if (testInput.getType() == 1){
            score = 9/(testInput.getTotalQuestion());
        }

        for (QuestionTestInput questionTestInput: questionTestInputList) {
            AnswerStudent answerStudent = findAnswerForQuestion(answerStudentList, questionTestInput);
            if (answerStudent != null) {
                // So sánh câu trả lời của sinh viên với đáp án chính xác của câu hỏi
                if (answerStudent.getContent().trim().equals(questionTestInput.getCorrectanswer().trim())) {
                    // Nếu câu trả lời đúng, tăng điểm lên
                    totalScore+=score;
                    answerStudent.setCorrect(true);
                    if (questionTestInput.getTestInputSession().getSession().getId() == 1) {
                        correctReading ++;
                    } else if (questionTestInput.getTestInputSession().getSession().getId() == 2) {
                        correctListening++;
                    } else if (questionTestInput.getTestInputSession().getSession().getId() == 3) {
                        correctVocabulary++;
                    } else if (questionTestInput.getTestInputSession().getSession().getId() == 4) {
                        correctGrammar++;
                    }
                } else {
                    answerStudent.setCorrect(false);
                }
                answerStudentRepository.save(answerStudent);
            }
        }

        for (TestInputSession testInputSession: testInput.getTestInputSessions()) {
            if (testInputSession.getSession().getId() == 1) {
                testInputStudent.setCorrectReading(correctReading);
            } else if (testInputSession.getSession().getId() == 2) {
                testInputStudent.setCorrectListening(correctListening);
            } else if (testInputSession.getSession().getId() == 3) {
                testInputStudent.setCorrectVocabulary(correctVocabulary);
            } else if (testInputSession.getSession().getId() == 4) {
                testInputStudent.setCorrectGrammar(correctGrammar);
            }

        }
        totalScore = roundToNearestHalf(totalScore);
        testInputStudent.setScore(totalScore);

        testInputStudentRepository.save(testInputStudent);
    }

    @Override
    public TestInputStudentDTO getresultTest(String code) {
        TestInputStudent testInputStudent = testInputStudentRepository.findByCode(code);
        if (testInputStudent == null){
            throw  new RuntimeException("Student Not Found");
        }
        TestInputStudentDTO testInputStudentDTO = TestInputStudentDTO.builder()
                .id(testInputStudent.getId())
                .code(testInputStudent.getCode())
                .correctReading(testInputStudent.getCorrectReading())
                .correctGrammar(testInputStudent.getCorrectGrammar())
                .correctListening(testInputStudent.getCorrectListening())
                .correctVocabulary(testInputStudent.getCorrectVocabulary())
                .score(testInputStudent.getScore())
                .time(testInputStudent.getTime())
                .createdBy(testInputStudent.getCreatedBy())
                .createdDate(testInputStudent.getCreatedDate())
                .modifiedBy(testInputStudent.getModifiedBy())
                .modifiedDate(testInputStudent.getModifiedDate())
                .build();

        for (TestInputSession testInputSession: testInputStudent.getTestInput().getTestInputSessions()) {
            if (testInputSession.getSession().getId() == 1){
                testInputStudentDTO.setTotalQuestionReading(testInputSession.getTotalQuestion());
            } else if (testInputSession.getSession().getId() == 2) {
                testInputStudentDTO.setTotalQuestionListening(testInputSession.getTotalQuestion());
            } else if (testInputSession.getSession().getId() == 3) {
                testInputStudentDTO.setTotalQuestionVocabulary(testInputSession.getTotalQuestion());
            } else if (testInputSession.getSession().getId() == 4) {
                testInputStudentDTO.setTotalQuestionGrammar(testInputSession.getTotalQuestion());
            }
        }

        return testInputStudentDTO;
    }

    @Override
    public List<QuestionTestInputDetailResult> getresultDetailTest(String code) {
        TestInputStudent testInputStudent = testInputStudentRepository.findByCode(code);
        if (testInputStudent == null){
            throw  new RuntimeException("Student Not Found");
        }

        List<QuestionTestInput> questionTestInputList = testInputStudent.getTestInput().getTestInputSessions()
                .stream()
                .flatMap(session -> session.getQuestionTestInputs().stream())
                .collect(Collectors.toList());
        questionTestInputList.sort(Comparator.comparingInt(QuestionTestInput::getOrderTop));

        List<QuestionTestInputDetailResult> questionTestInputDetailResultList = new ArrayList<>();

        for (QuestionTestInput questionTestInput: questionTestInputList) {
            AnswerStudent answerStudent = answerStudentRepository.findByQuestionTestInputAndTestInputStudent(questionTestInput, testInputStudent);

            if (answerStudent == null){
                throw new RuntimeException("Not Found");
            }

            QuestionTestInputDetailResult questionTestInputDetailResult = QuestionTestInputDetailResult.builder()
                    .id(questionTestInput.getId())
                    .audiomp3(questionTestInput.getAudiomp3())
                    .image(questionTestInput.getImage())
                    .paragraph(questionTestInput.getParagraph())
                    .title(questionTestInput.getTitle())
                    .option1(questionTestInput.getOption1())
                    .option2(questionTestInput.getOption2())
                    .option3(questionTestInput.getOption3())
                    .option4(questionTestInput.getOption4())
                    .answerCorrect(questionTestInput.getCorrectanswer())
                    .answerForStudent(answerStudent.getContent())
                    .result(answerStudent.isCorrect())
                    .type(questionTestInput.getType())
                    .part(questionTestInput.getPart())
                    .orderTop(questionTestInput.getOrderTop())
                    .build();

            questionTestInputDetailResultList.add(questionTestInputDetailResult);
        }

        return questionTestInputDetailResultList;
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

    private AnswerStudent findAnswerForQuestion(List<AnswerStudent> studentAnswers, QuestionTestInput questionTestInput) {
        for (AnswerStudent answer : studentAnswers) {
            if (answer.getQuestionTestInput().equals(questionTestInput)) {
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
