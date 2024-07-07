package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.certificate.CertificateDTO;
import com.englishacademy.EnglishAcademy.models.certificate.CreateCertificate;

import java.util.List;

public interface CertificateService {
    CertificateDTO create(CreateCertificate certificate);

    CertificateDTO createOf(CreateCertificate certificate);

    CertificateDTO getCertificate(String code);

    List<CertificateDTO> getCertificateList(Long id);
}
