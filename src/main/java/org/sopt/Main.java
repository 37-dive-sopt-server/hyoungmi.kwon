package org.sopt;

import org.sopt.controller.MemberController;
import org.sopt.domain.Member;
import org.sopt.repository.FileMemberRepository;
import org.sopt.repository.MemberRepository;
import org.sopt.service.MemberService;
import org.sopt.service.MemberServiceImpl;
import org.sopt.view.MainView;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        MainView mainView = new MainView();
        MemberRepository memberRepository = new FileMemberRepository();
        MemberService memberService = new MemberServiceImpl(memberRepository);
        MemberController memberController = new MemberController(memberService);

        while (true) {
            mainView.showMenu();
            String choice = mainView.getMenuChoice();

            switch (choice) {
                case "1":
                    String name = mainView.getValidatedName();
                    String birthday = mainView.getValidatedBirthday();
                    // 20세 미만(birthday = null)일 경우 다시 메뉴를 보여주는 루프로 이동
                    if (birthday == null) {
                        break;
                    }
                    String email = mainView.getValidatedEmail();
                    String gender = mainView.getValidatedGender();

                    try {
                        Member newMember = memberController.createMember(name, birthday, email, gender);
                        mainView.showCreatedMember(newMember.getId());

                    } catch (IllegalArgumentException e) {
                        mainView.showError(e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        Long id = mainView.getMemberId(choice);
                        Optional<Member> foundMember = memberController.findMemberById(id);

                        if (foundMember.isPresent()) {
                            mainView.showMember(foundMember.get());
                        } else {
                            mainView.showMemberNotFound();
                        }
                    } catch (NumberFormatException e) {
                        mainView.showError(e.getMessage());
                    }
                    break;

                case "3":
                    List<Member> allMembers = memberController.getAllMembers();
                    mainView.showAllMembers(allMembers);
                    break;

                case "4":
                    try {
                        Long id = mainView.getMemberId(choice);
                        memberController.deleteMemberById(id);
                        mainView.showMemberDeleted(id);
                    } catch (IllegalArgumentException e) {
                        mainView.showError(e.getMessage());
                    }
                    break;

                case "5":
                    mainView.showEnd();
                    mainView.close();
                    return;

                default:
                    mainView.showInvalidMenuChoice();
            }
        }
    }
}