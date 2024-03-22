package com.englishacademy.EnglishAcademy.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String code;

    private String fullName;

    private String email;

    private String phone;

    private Integer status;
}
