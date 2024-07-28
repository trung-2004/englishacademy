package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.user.UserDTO;
import com.englishacademy.EnglishAcademy.entities.Role;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.repositories.UserRepository;
import com.englishacademy.EnglishAcademy.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAllByRole(Role.TEACHER);
        return users.stream().map(this::toDTO).toList();
    }
    private UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .dob(user.getDob())
                .phone(user.getPhone())
                .email(user.getEmail())
                .address(user.getAddress())
                .code(user.getCode())
                .fullName(user.getFullName())
                .status(user.getStatus())
                .build();
        return userDTO;
    }
}
