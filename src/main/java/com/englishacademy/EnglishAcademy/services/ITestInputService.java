package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.testInput.TestInputDTO;
import com.englishacademy.EnglishAcademy.dtos.testInput.TestInputDetail;

import java.util.List;

public interface ITestInputService {
    List<TestInputDTO> findAllToiec();
    List<TestInputDTO> findAllIelts();
    TestInputDetail getdetailTest(String slug);

}
