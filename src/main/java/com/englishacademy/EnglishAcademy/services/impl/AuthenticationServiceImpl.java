package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.auth.JwtAuthenticationResponse;
import com.englishacademy.EnglishAcademy.dtos.student.StudentDTO;
import com.englishacademy.EnglishAcademy.dtos.user.UserDTO;
import com.englishacademy.EnglishAcademy.entities.Role;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.models.auth.*;
import com.englishacademy.EnglishAcademy.models.mail.MailStructure;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.repositories.UserRepository;
import com.englishacademy.EnglishAcademy.services.AuthenticationService;
import com.englishacademy.EnglishAcademy.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final MailService mailService;
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
    public User signup(SignUpRequest signUpRequest) {
        User user = new User();
        user.setCode(generateRandomString(8));
        user.setFullName(signUpRequest.getFullname());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new AppException(ErrorCode.INVALIDEMAILORPASSWORD));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));

        var jwt = jwtService.generateToken(user);

        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
            String useEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
            User user = userRepository.findByEmail(useEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
                var jwt = jwtService.generateToken(user);

                JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

                jwtAuthenticationResponse.setToken(jwt);
                jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
                return jwtAuthenticationResponse;
            }
            return null;
    }

    @Override
    public UserDTO profile(User currentUser) {
        if (currentUser == null) throw new AppException(ErrorCode.NOTFOUND);
        UserDTO userDTO = UserDTO.builder()
                .id(currentUser.getId())
                .code(currentUser.getCode())
                .fullName(currentUser.getFullName())
                .email(currentUser.getEmail())
                .phone(currentUser.getPhone())
                .status(currentUser.getStatus())
                .build();
        return userDTO;
    }

    @Override
    public Student studentSignUp(SignUpRequest signUpRequest) {
        Student student = new Student();
        student.setCode(generateRandomString(8));
        student.setFullName(signUpRequest.getFullname());
        student.setEmail(signUpRequest.getEmail());
        student.setRole(Role.STUDENT);
        student.setUserType("student");
        student.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return studentRepository.save(student);
    }

    @Override
    public JwtAuthenticationResponse studentSignIn(SignInRequest signInRequest) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                    signInRequest.getPassword()));

            var student = studentRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new AppException(ErrorCode.INVALIDEMAILORPASSWORD));
            var jwt = jwtService.generateToken2(student);

            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), student);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            return jwtAuthenticationResponse;
    }

    @Override
    public StudentDTO studentProfile(Student currentStudent) {
        if (currentStudent == null) throw new AppException(ErrorCode.NOTFOUND);
        StudentDTO studentDTO = StudentDTO.builder()
                .id(currentStudent.getId())
                .code(currentStudent.getCode())
                .fullName(currentStudent.getFullName())
                .email(currentStudent.getEmail())
                .phone(currentStudent.getPhone())
                .status(currentStudent.getStatus())
                .gender(currentStudent.getGender())
                .dayOfBirth(currentStudent.getDayOfBirth())
                .startDate(currentStudent.getStartDate())
                .createdBy(currentStudent.getCreatedBy())
                .createdDate(currentStudent.getCreatedDate())
                .modifiedBy(currentStudent.getModifiedBy())
                .modifiedDate(currentStudent.getModifiedDate())
                .build();
        return studentDTO;
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest, User user) {
        System.out.println(changePasswordRequest.toString());
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect current password");
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())){
            throw new RuntimeException("Incorrect current password");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User user = userRepository.findByEmail(forgotPasswordRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, 1);


        String resetToken = generateResetToken();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(calendar.getTime());
        userRepository.save(user);

        String resetLink = "http://localhost:3001/reset-password/" + resetToken;

        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("Password Reset");
        mailStructure.setMessage("Click the link to reset your password: " + resetLink);

        mailService.sendMail(forgotPasswordRequest.getEmail(), mailStructure);
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest, String token) {
        User user = userRepository.findByEmail(resetPasswordRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        Date now = new Date();

        if (token == null || !user.getResetToken().equals(token) || !user.getEmail().equals(resetPasswordRequest.getEmail()) || user.getResetTokenExpiry().before(now))
        {
            throw new AppException(ErrorCode.INVALID_RESETTOKEN);
        }

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }

    @Override
    public void studentChangePassword(ChangePasswordRequest changePasswordRequest, Student student) {
        System.out.println(changePasswordRequest.toString());
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), student.getPassword())) {
            throw new RuntimeException("Incorrect current password");
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())){
            throw new RuntimeException("Incorrect current password");
        }
        student.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        studentRepository.save(student);
    }

    @Override
    public void studentForgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        Student student = studentRepository.findByEmail(forgotPasswordRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, 1);


        String resetToken = generateResetToken();
        student.setResetToken(resetToken);
        student.setResetTokenExpiry(calendar.getTime());
        studentRepository.save(student);

        String resetLink = "http://localhost:3000/reset-password/" + resetToken;

        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("Password Reset");
        mailStructure.setMessage("Click the link to reset your password: " + resetLink);

        mailService.sendMail(forgotPasswordRequest.getEmail(), mailStructure);
    }

    @Override
    public void studentResetPassword(ResetPasswordRequest resetPasswordRequest, String token) {
        Student student = studentRepository.findByEmail(resetPasswordRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        Date now = new Date();

        if (token == null || !student.getResetToken().equals(token) || !student.getEmail().equals(resetPasswordRequest.getEmail()) || student.getResetTokenExpiry().before(now))
        {
            throw new AppException(ErrorCode.INVALID_RESETTOKEN);
        }

        student.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        student.setResetToken(null);
        student.setResetTokenExpiry(null);
        studentRepository.save(student);
    }

    private String generateResetToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
