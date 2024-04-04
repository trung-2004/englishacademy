package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDetail;

public interface ISubjectService {
    SubjectDetail getDetail(String slug, Long studentId);
}
