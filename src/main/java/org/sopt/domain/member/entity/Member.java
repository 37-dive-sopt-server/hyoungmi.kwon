package org.sopt.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthdate;
    private String email;
    private Gender gender;

    public Member(String name, LocalDate birthdate, String email, Gender gender) {
        this.name = name;
        this.birthdate = birthdate;
        this.email = email;
        this.gender = gender;
    }

    public static Member create(String name, LocalDate birthdate, String email, Gender gender) {
        return new Member(name, birthdate, email, gender);
    }

}