package com.englishacademy.EnglishAcademy.dtos.potential_customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PotentialCustomerDTO {
    private Long id;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
    private String fullname;
    private String email;
    private String phone;
    private Integer status;
}
