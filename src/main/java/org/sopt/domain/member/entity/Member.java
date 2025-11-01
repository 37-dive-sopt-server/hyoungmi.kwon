package org.sopt.domain.member.entity;

import org.sopt.common.utils.IdGenerator;
import java.time.LocalDate;

public class Member {

    private Long id;
    private String name;
    private LocalDate birthdate;
    private String email;
    private Gender gender;

    public Member(Long id, String name, LocalDate birthdate, String email, Gender gender) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.email = email;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public static Member create(String name, LocalDate birthdate, String email, Gender gender) {
        return new Member(IdGenerator.next(), name, birthdate, email, gender);
    }

    public static Member restore(Long id, String name, LocalDate birthdate, String email, Gender gender) {
        return new Member(id, name, birthdate, email, gender);
    }
}