package org.sopt.domain.member.entity;

import java.time.LocalDate;

public class Member {

    private Long id;
    private String name;
    private LocalDate birthday;
    private String email;
    private Gender gender;

    public Member(Long id, String name, LocalDate birthday, String email, Gender gender) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }
}