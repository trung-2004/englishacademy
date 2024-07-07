package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity implements UserDetails {
    @Column(name = "code", nullable = false, length = 12)
    private String code;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "dob")
    private LocalDate dob;
    @Column(name = "status")
    private Integer status;
    @Column(name = "resetToken")
    private String resetToken;
    @Column(name = "resetTokenExpiry")
    private Date resetTokenExpiry;
    private Role role;
    private String userType;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    private List<Classes> classes;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Tutor> tutors = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
