package com.englishacademy.EnglishAcademy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject {
    private boolean status;
    private String message;
    private Object data;
}
