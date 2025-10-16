package org.sopt.view;

import org.sopt.common.exception.ErrorCode;
import org.sopt.common.exception.MemberException;
import org.sopt.domain.Member;
import org.sopt.validator.MemberValidator;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

// 입출력 담당
public class MainView {

    Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        System.out.println("\n✨ --- DIVE SOPT 회원 관리 서비스 --- ✨");
        System.out.println("---------------------------------");
        System.out.println("1️⃣. 회원 등록 ➕");
        System.out.println("2️⃣. ID로 회원 조회 🔍");
        System.out.println("3️⃣. 전체 회원 조회 📋");
        System.out.println("4️⃣. 회원 삭제 🗑");
        System.out.println("5️⃣. 종료 🚪");
        System.out.println("---------------------------------");
        System.out.print("메뉴를 선택하세요: ");
    }

    public String getMenuChoice() {
        return scanner.nextLine();
    }

    public String getValidatedName() {
        while (true) {
            System.out.print("등록할 회원 이름을 입력하세요: ");
            String name = scanner.nextLine();

            try {
                MemberValidator.validateName(name);
                return name;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String getValidatedBirthday() {
        while (true) {
            System.out.print("등록할 회원의 생년월일을 입력하세요(YYYY-MM-DD): ");
            String birthdayStr = scanner.nextLine();

            try {
                LocalDate birthday  = MemberValidator.validateBirthday(birthdayStr);
                MemberValidator.validateAge(birthday);
                return birthdayStr;
            } catch (MemberException e) {
                System.out.println(e.getMessage());

                if (e.getErrorCode() == ErrorCode.UNDER_AGE) {
                    // 20세 미만일 경우 null 반환
                    return null;
                }
            }
        }
    }

    public String getValidatedEmail() {
        while (true) {
            System.out.print("등록할 회원의 이메일을 입력하세요: ");
            String email = scanner.nextLine();

            try {
                MemberValidator.validateEmail(email);
                return email;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String getValidatedGender() {
        while (true) {
            System.out.print("등록할 회원의 성별을 입력하세요 (MALE/FEMALE): ");
            String gender = scanner.nextLine();

            try {
                MemberValidator.validateGender(gender);
                return gender;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Long getMemberId(String choice ) {
        if (choice.equals("2")) {
            System.out.print("조회할 회원 ID를 입력하세요: ");
        } else if (choice.equals("4")) {
            System.out.print("삭제할 회원의 ID를 입력하세요: ");
        }

        return Long.parseLong(scanner.nextLine());
    }

    public void showCreatedMember(Long createdId) {
        System.out.println("✅ 회원 등록 완료 (ID: " + createdId + ")");
    }

    public void showMember(Member member) {
        System.out.println("✅ 조회된 회원: ID=" + member.getId() + ", 이름=" + member.getName());
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showMemberNotFound() {
        System.out.println("⚠️ 해당 ID의 회원을 찾을 수 없습니다.");
    }

    public void showAllMembers(List<Member> members) {
        if (members.isEmpty()) {
            System.out.println("ℹ️ 등록된 회원이 없습니다.");
        }
        else {
            System.out.println("--- 📋 전체 회원 목록 📋 ---");
            for (Member member : members) {
                System.out.println("👤 ID=" + member.getId() + ", 이름=" + member.getName());
            }
            System.out.println("--------------------------");
        }
    }

    public void showMemberDeleted(Long id) {
        System.out.println("✅ 삭제된 회원: ID=" + id);
    }

    public void showEnd() {
        System.out.println("👋 서비스를 종료합니다. 안녕히 계세요!");
    }

    public void close() {
        scanner.close();
    }

    public void showInvalidMenuChoice() {
        System.out.println("🚫 잘못된 메뉴 선택입니다. 다시 시도해주세요.");
    }
}
