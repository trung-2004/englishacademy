package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.ItemOnlineMapper;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.ItemOnlineRepository;
import com.englishacademy.EnglishAcademy.repositories.ItemOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.services.IItemOnlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ItemOnlineService implements IItemOnlineService {
    private final ItemOnlineRepository itemOnlineRepository;
    private final ItemOnlineMapper itemOnlineMapper;
    private final StudentRepository studentRepository;
    private final CourseOnlineStudentRepository courseOnlineStudentRepository;
    private final ItemOnlineStudentRepository itemOnlineStudentRepository;

    @Override
    public ItemOnlineDetail getItemOnlineDetail(String slug) {
        ItemOnline model = itemOnlineRepository.findBySlug(slug);
        if (model == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        return itemOnlineMapper.toItemOnlineDetail(model);
    }

    @Override
    public void completeItem(String slug, Long studentId) {
        ItemOnline model = itemOnlineRepository.findBySlug(slug);
        if (model == null) throw new AppException(ErrorCode.ITEMONLINE_NOTFOUND);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));

        CourseOnlineStudent courseOnlineStudent = courseOnlineStudentRepository.findByCourseOnlineAndStudent(model.getTopicOnline().getCourseOnline(), student);
        if (courseOnlineStudent == null) throw new AppException(ErrorCode.COURSE_NOTPURCHASED);

        ItemOnlineStudent itemOnlineStudentExsiting = itemOnlineStudentRepository.findByItemOnlineAndStudent(model, student);
        if (itemOnlineStudentExsiting == null) throw new AppException(ErrorCode.NOTFOUND);

        if (itemOnlineStudentExsiting.isStatus()) {
            return;
        }

        itemOnlineStudentExsiting.setStatus(true);
        itemOnlineStudentRepository.save(itemOnlineStudentExsiting);
    }
}
