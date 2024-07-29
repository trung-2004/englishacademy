package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.classes.CLassesDTO;
import com.englishacademy.EnglishAcademy.dtos.classes.RoomDTO;
import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDTO;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.ClassesMapper;
import com.englishacademy.EnglishAcademy.models.classes.CreateClasses;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.ClassesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassesServiceImpl implements ClassesService {
    private final ClassesRepository classesRepository;
    private final CourseOfflineRepository courseOfflineRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;
    private final CourseOfflineStudentRepository courseOfflineStudentRepository;
    private final ClassesSlotRepository classesSlotRepository;
    private final ClassesTestOfflineRepository classesTestOfflineRepository;
    private final ClassesMapper classesMapper;

    @Override
    public List<CLassesDTO> getAllByTeacher(User currentUser) {
        return classesRepository.findAllByTeacherAndStatus(currentUser, true).stream().map(classesMapper::toClassesDTO).collect(Collectors.toList());
    }

    @Override
    public CLassesDTO create(CreateClasses createClasses) {
        CourseOffline courseOffline = courseOfflineRepository.findById(createClasses.getCourseOfflineId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOTFOUND));
        User teacher = userRepository.findById(createClasses.getTeacherId()).orElseThrow(()-> new AppException(ErrorCode.NOTFOUND));
        Room room = roomRepository.findById(createClasses.getRoomId()).orElseThrow(()-> new AppException(ErrorCode.NOTFOUND));
        Classes classes = Classes.builder()
                .name(createClasses.getName())
                .status(false)
                .teacher(teacher)
                .room(room)
                .build();
        classesRepository.save(classes);

        CourseOfflineStudent courseOfflineStudent = CourseOfflineStudent.builder()
                .courseOffline(courseOffline)
                .classes(classes)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .status(true)
                .build();
        courseOfflineStudentRepository.save(courseOfflineStudent);

        for (Subject subject: courseOffline.getSubjects()){
            for (Slot slot: subject.getSlots()){
                ClassesSlot classesSlot = ClassesSlot.builder()
                        .classes(classes)
                        .slot(slot)
                        .time(slot.getName())
                        .build();
                classesSlotRepository.save(classesSlot);
            }
            for (TestOffline testOffline: subject.getTestOfflines()){
                ClassesTestOffline classesTestOffline = ClassesTestOffline.builder()
                        .classes(classes)
                        .testOffline(testOffline)
                        .time(testOffline.getStartDate().toString())
                        .build();
                classesTestOfflineRepository.save(classesTestOffline);
            }
        }

        for (Long id: createClasses.getStudentIds()){
            Student student = studentRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.NOTFOUND));
            student.setClasses(classes);
            studentRepository.save(student);
        }

        return classesMapper.toClassesDTO(classes);
    }

    @Override
    public int countClassesByTeacher(User currentUser) {
        return classesRepository.findAllByTeacherCount(currentUser.getId());
    }

    @Override
    public List<CLassesDTO> getAll(User currentUser) {
        return classesRepository.findAll().stream().map(classesMapper::toClassesDTO).collect(Collectors.toList());
    }

    @Override
    public List<RoomDTO> getAllRoom(User currentUser) {
        List<RoomDTO> list = new ArrayList<>();
        List<Room> rooms = roomRepository.findAll();
        for (Room room : rooms) {
            RoomDTO roomDTO = RoomDTO.builder()
                    .id(room.getId())
                    .name(room.getName())
                    .createdBy(room.getCreatedBy())
                    .createdDate(room.getCreatedDate())
                    .modifiedDate(room.getModifiedDate())
                    .modifiedBy(room.getModifiedBy())
                    .build();
            list.add(roomDTO);
        }
        return list;
    }
}
