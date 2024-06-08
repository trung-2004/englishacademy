package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.repositories.UserRepository;
import com.englishacademy.EnglishAcademy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                /*return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));*/
                Optional<User> userOptional = userRepository.findByEmail(username);
                Optional<Student> studentOptional = studentRepository.findByEmail(username);

                if (userOptional.isPresent() && "user".equals(userOptional.get().getUserType())) {
                    return userOptional.get();
                } else if (studentOptional.isPresent() && "student".equals(studentOptional.get().getUserType())) {
                    return studentOptional.get();
                }

                throw new AppException(ErrorCode.INVALIDEMAILORPASSWORD);
            }
        };
    }
}
