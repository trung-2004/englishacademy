package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDetail;

public interface SubjectService {
    SubjectDetail getDetail(String slug, Long studentId);
}
