package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.answerStudentItemSlot.ListScore;
import com.englishacademy.EnglishAcademy.entities.AnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.entities.ItemSlot;
import com.englishacademy.EnglishAcademy.entities.PeerReview;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.models.answerStudent.ScoreAnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.repositories.AnswerStudentItemSlotRepository;
import com.englishacademy.EnglishAcademy.repositories.ItemSlotRepository;
import com.englishacademy.EnglishAcademy.repositories.PeerReviewRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.services.IAnswerStudentItemSlotService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class AnswerStudentItemSlotService implements IAnswerStudentItemSlotService {
    private final AnswerStudentItemSlotRepository answerStudentItemSlotRepository;
    private final ItemSlotRepository itemSlotRepository;
    private final StudentRepository studentRepository;
    private final PeerReviewRepository peerReviewRepository;

    public AnswerStudentItemSlotService(
            AnswerStudentItemSlotRepository answerStudentItemSlotRepository,
            ItemSlotRepository itemSlotRepository,
            StudentRepository studentRepository,
            PeerReviewRepository peerReviewRepository
    ) {
        this.answerStudentItemSlotRepository = answerStudentItemSlotRepository;
        this.itemSlotRepository = itemSlotRepository;
        this.studentRepository = studentRepository;
        this.peerReviewRepository = peerReviewRepository;
    }

    @Override
    public AnswerStudentItemSlot save(CreateAnswerStudentItemSlot model, Long studentId) {
        // check
        ItemSlot itemSlot = itemSlotRepository.findById(model.getItemSlotId())
                .orElseThrow(()-> new AppException(ErrorCode.ITEMSLOT_NOTFOUND));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));
        AnswerStudentItemSlot answerStudentItemSlotExsting = answerStudentItemSlotRepository.findByStudentAndItemSlot(student, itemSlot);
        if (answerStudentItemSlotExsting != null) throw new AppException(ErrorCode.ANSWERITEMSLOT_EXISTING);
        Date now = new Timestamp(System.currentTimeMillis());
        /*System.out.println(now);
        System.out.println(itemSlot.getStartDate());
        System.out.println(itemSlot.getEndDate());*/
        if (now.after(itemSlot.getEndDate()) || now.before(itemSlot.getStartDate())) throw new AppException(ErrorCode.EXPIRES);

        AnswerStudentItemSlot answerStudentItemSlot = AnswerStudentItemSlot.builder()
                .itemSlot(itemSlot)
                .student(student)
                .star(0)
                .time(now)
                .content(model.getContent())
                .star1Count(3)
                .star2Count(3)
                .star3Count(3)
                .build();
        answerStudentItemSlot.setCreatedBy(student.getFullName());
        answerStudentItemSlot.setModifiedBy(student.getFullName());
        answerStudentItemSlot.setCreatedDate((Timestamp) now);
        answerStudentItemSlot.setModifiedDate((Timestamp) now);

        answerStudentItemSlotRepository.save(answerStudentItemSlot);
        return answerStudentItemSlot;
    }

    @Override
    public AnswerStudentItemSlot scoreAnswer(ScoreAnswerStudentItemSlot scoreAnswerStudentItemSlot, Long studentId) {
        // check
        AnswerStudentItemSlot answerStudentItemSlot = answerStudentItemSlotRepository.findById(scoreAnswerStudentItemSlot.getAnswerStudentItemSlotId())
                .orElseThrow(()-> new AppException(ErrorCode.ANSWERITEMSLOT_NOTFOUND));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));
        AnswerStudentItemSlot answerStudentItemSlotExsting = answerStudentItemSlotRepository.findByStudentAndItemSlot(student, answerStudentItemSlot.getItemSlot());
        if (answerStudentItemSlotExsting == null) throw new AppException(ErrorCode.ANSWERITEMSLOT_EXISTING);
        Date now = new Timestamp(System.currentTimeMillis());

        PeerReview peerReviewExsting = peerReviewRepository.findByStudentAndAnswerStudentItemSlot(student, answerStudentItemSlot);
        if (peerReviewExsting != null){
            throw new AppException(ErrorCode.ANSWERITEMSLOT_GRADED);
        }
        PeerReview peerReview = PeerReview.builder()
                .answerStudentItemSlot(answerStudentItemSlot)
                .star(scoreAnswerStudentItemSlot.getStar())
                .student(student)
                .build();
        peerReviewRepository.save(peerReview);

        // logic
        if (now.after(answerStudentItemSlot.getItemSlot().getEndDate()) || now.before(answerStudentItemSlot.getItemSlot().getStartDate())) throw new AppException(ErrorCode.EXPIRES);
        if (scoreAnswerStudentItemSlot.getStar().equals(1) && answerStudentItemSlotExsting.getStar1Count() > 0) {
            answerStudentItemSlot.setStar(answerStudentItemSlot.getStar()+scoreAnswerStudentItemSlot.getStar());
            answerStudentItemSlotExsting.setStar1Count(answerStudentItemSlotExsting.getStar1Count() - 1);
            answerStudentItemSlotRepository.save(answerStudentItemSlot);
            answerStudentItemSlotRepository.save(answerStudentItemSlotExsting);
        } else if (scoreAnswerStudentItemSlot.getStar().equals(2) && answerStudentItemSlotExsting.getStar2Count() > 0) {
            answerStudentItemSlot.setStar(answerStudentItemSlot.getStar()+scoreAnswerStudentItemSlot.getStar());
            answerStudentItemSlotExsting.setStar2Count(answerStudentItemSlotExsting.getStar2Count() - 1);
            answerStudentItemSlotRepository.save(answerStudentItemSlot);
            answerStudentItemSlotRepository.save(answerStudentItemSlotExsting);
        } else if (scoreAnswerStudentItemSlot.getStar().equals(3) && answerStudentItemSlotExsting.getStar3Count() > 0) {
            answerStudentItemSlot.setStar(answerStudentItemSlot.getStar()+scoreAnswerStudentItemSlot.getStar());
            answerStudentItemSlotExsting.setStar3Count(answerStudentItemSlotExsting.getStar3Count() - 1);
            answerStudentItemSlotRepository.save(answerStudentItemSlot);
            answerStudentItemSlotRepository.save(answerStudentItemSlotExsting);
        } else {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        // save
        return answerStudentItemSlot;
    }

    @Override
    public ListScore listScore(String slug, Long id) {
        ItemSlot itemSlot = itemSlotRepository.findBySlug(slug);
        if(itemSlot == null) throw  new AppException(ErrorCode.ITEMSLOT_NOTFOUND);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));
        AnswerStudentItemSlot answerStudentItemSlotExsting = answerStudentItemSlotRepository.findByStudentAndItemSlot(student, itemSlot);
        if (answerStudentItemSlotExsting == null) throw new AppException(ErrorCode.ANSWERITEMSLOT_NOTFOUND);
        ListScore listScore = ListScore.builder()
                .star1Count(answerStudentItemSlotExsting.getStar1Count())
                .star2Count(answerStudentItemSlotExsting.getStar2Count())
                .star3Count(answerStudentItemSlotExsting.getStar3Count())
                .build();
        return listScore;
    }
}
