package org.sopt.domain.member.entity;

import jakarta.persistence.*;
import org.sopt.common.utils.IdGenerator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthdate;
    private String email;
    private Gender gender;

    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    public Member(Long id, String name, LocalDate birthdate, String email, Gender gender) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.email = email;
        this.gender = gender;
    }

    public Member() {}

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