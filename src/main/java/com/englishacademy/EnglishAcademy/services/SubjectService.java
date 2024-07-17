package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDTO;
import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDTOResponse;
import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDetail;
import com.englishacademy.EnglishAcademy.models.subject.CreateSubject;
import com.englishacademy.EnglishAcademy.models.subject.EditSubject;

import java.util.List;

public interface SubjectService {
    SubjectDetail getDetail(String slug, Long studentId);
    SubjectDetail getDetailByUser(String slug, Long studentId, Long classId);
    SubjectDTO create(CreateSubject createSubject);
    SubjectDTO edit(EditSubject editSubject);
    void delete(Long[] ids);
    SubjectDTO getBySlug(String slug);
    List<SubjectDTO> getAllByCourseSlug(String slug);
    List<SubjectDTOResponse> getAllScoreByCourseSlug(String slug, Long studentId);
}
