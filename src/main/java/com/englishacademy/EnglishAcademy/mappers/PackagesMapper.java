package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.packages.PackageDTO;
import com.englishacademy.EnglishAcademy.dtos.payment.PaymentDTO;
import com.englishacademy.EnglishAcademy.entities.Packages;
import com.englishacademy.EnglishAcademy.entities.Payment;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class PackagesMapper {
    public PackageDTO toPackageDTO(Packages model){
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        PackageDTO packageDTO = PackageDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .hourlyRate(model.getHourlyRate())
                .description(model.getDescription())
                .numSessions(model.getNumSessions())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return packageDTO;
    }
}
