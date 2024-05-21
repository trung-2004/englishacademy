package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.tutor.TutorDTO;
import com.englishacademy.EnglishAcademy.dtos.tutor.TutorDetail;
import com.englishacademy.EnglishAcademy.entities.Availability;
import com.englishacademy.EnglishAcademy.entities.Tutor;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.TutorMapper;
import com.englishacademy.EnglishAcademy.models.availabilitiy.CreateAvailability;
import com.englishacademy.EnglishAcademy.models.tutor.CreateTutor;
import com.englishacademy.EnglishAcademy.repositories.AvailabilityRepository;
import com.englishacademy.EnglishAcademy.repositories.TutorRepository;
import com.englishacademy.EnglishAcademy.repositories.UserRepository;
import com.englishacademy.EnglishAcademy.services.ITutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TutorService implements ITutorService {
    private final UserRepository userRepository;
    private final TutorRepository tutorRepository;
    private final AvailabilityRepository availabilityRepository;
    private final TutorMapper tutorMapper;
    private final ImageStorageService storageService;
    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            sb.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
    @Override
    public List<TutorDTO> findAll() {
        List<TutorDTO> tutorDTOList = tutorRepository.findAllByStatus(true).stream()
                .map(tutorMapper::toTutorDTO).collect(Collectors.toList());
        return tutorDTOList;
    }

    @Override
    public TutorDetail getDetail(String code) {
        Tutor tutor = tutorRepository.findByCode(code);
        if (tutor == null) throw new AppException(ErrorCode.NOTFOUND);
        TutorDetail tutorDetail = tutorMapper.toTutorDetail(tutor);
        return tutorDetail;
    }

    @Override
    public void save(CreateTutor createTutor) {
        User user = userRepository.findById(createTutor.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));
        Tutor tutorExsiting = tutorRepository.findByUser(user);
        if (tutorExsiting != null) throw new AppException(ErrorCode.NOTFOUND);

        String generatedFileName = storageService.storeFile(createTutor.getAvatar());
        Tutor tutor = Tutor.builder()
                .code(user.getFullName().toLowerCase().replace(" ", "-")+"-"+generateRandomString(5))
                .level(createTutor.getLevel())
                .address(createTutor.getAddress())
                .status(false)
                .hourlyRate(createTutor.getHourlyRate())
                .avatar("http://localhost:8080/api/v1/FileUpload/files/"+generatedFileName)
                .cetificate(createTutor.getCetificate())
                .experience(createTutor.getExperience())
                .teachingSubject(createTutor.getTeachingSubject())
                .user(user)
                .build();
        tutor.setCreatedBy(user.getFullName());
        tutor.setModifiedBy(user.getFullName());
        tutor.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        tutor.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        tutorRepository.save(tutor);

    }
}
