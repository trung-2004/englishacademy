package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.PotentialCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PotentialCustomerRepository extends JpaRepository<PotentialCustomer, Long> {
    PotentialCustomer findByEmailAndPhone(String email, String phone);
}
