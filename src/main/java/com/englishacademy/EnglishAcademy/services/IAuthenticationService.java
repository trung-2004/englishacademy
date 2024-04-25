package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.auth.JwtAuthenticationResponse;
import com.englishacademy.EnglishAcademy.dtos.student.StudentDTO;
import com.englishacademy.EnglishAcademy.dtos.user.UserDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.models.auth.*;

public interface IAuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    UserDTO profile(User currentUser);
    void changePassword(ChangePasswordRequest changePasswordRequest, User user);
    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    void resetPassword(ResetPasswordRequest resetPasswordRequest, String token);


    Student studentSignUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse studentSignIn(SignInRequest signInRequest);
    StudentDTO studentProfile(Student currentStudent);
    void studentChangePassword(ChangePasswordRequest changePasswordRequest, Student student);
    void studentForgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    void studentResetPassword(ResetPasswordRequest resetPasswordRequest, String token);
}
