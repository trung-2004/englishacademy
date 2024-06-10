package com.englishacademy.EnglishAcademy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "student")
public class Student extends BaseEntity implements UserDetails {
    @Column(name = "code", nullable = false, length = 12)
    private String code;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private Integer status;

    @Column(name = "resetToken")
    private String resetToken;

    @Column(name = "resetTokenExpiry")
    private Date resetTokenExpiry;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "dayOfBirth")
    private LocalDate dayOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "startDate")
    private Date startDate;

    private Role role;
    private String userType;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<CourseOnlineStudent> courseOnlineStudents;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<TestInputStudent> testInputStudents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classId")
    private Classes classes;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<AnswerStudentItemSlot> answerStudentItemSlots;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<PeerReview> peerReviews;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<ItemOnlineStudent> itemOnlineStudents;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<TestOfflineStudent> testOfflineStudents;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<TestOnlineStudent> testOnlineStudents;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<StudentPackage> studentPackages;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Booking> bookings;


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

    public Long getUserId() {
        return super.getId(); // Giả sử getId() là phương thức trong lớp BaseEntity để lấy id
    }

}
