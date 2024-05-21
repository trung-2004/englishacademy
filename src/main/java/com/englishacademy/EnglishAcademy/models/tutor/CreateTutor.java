package com.englishacademy.EnglishAcademy.models.tutor;

import com.englishacademy.EnglishAcademy.models.availabilitiy.CreateAvailability;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CreateTutor implements Serializable{
    private Long userId;
    private String phone;
    private Integer level;
    private MultipartFile avatar;
    private String address;
    private String cetificate;
    private String experience;
    private String teachingSubject;
    private Integer hourlyRate;
}
