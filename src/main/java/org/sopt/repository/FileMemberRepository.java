package org.sopt.repository;

import org.sopt.common.exception.ErrorCode;
import org.sopt.common.exception.MemberException;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileMemberRepository implements MemberRepository {

    private static final String FILE_PATH = "members.txt";
    private final Map<Long, Member> store = new HashMap<>();
    private static final String SEPARATOR = ",";
    private Long sequence = 0L;

    public FileMemberRepository() {
        loadFromFile();
    }

    @Override
    public Member save(Member member) {
        Member newMember = new Member(++sequence, member.getName(),
                member.getBirthday(), member.getEmail(),
                member.getGender());
        store.put(newMember.getId(), newMember);
        saveToFile();
        return newMember;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsByEmail(String email) {
        return store.values().stream()
                .anyMatch(member -> member.getEmail().equals(email));
    }

    @Override
    public void deleteById(Long id) {
        if (!store.containsKey(id)) {
            throw new IllegalArgumentException(ErrorCode.MEMBER_NOT_FOUND.getMessage());
        }
        store.remove(id);
        saveToFile();
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(SEPARATOR);
                Long id = Long.parseLong(split[0]);
                String name = split[1];
                LocalDate birthday = LocalDate.parse(split[2]);
                String email = split[3];
                Gender gender = Gender.valueOf(split[4]);

                Member member = new Member(id, name, birthday, email, gender);
                store.put(id, member);

                if (id > sequence) {
                    sequence = id;
                }
            }
        } catch (FileNotFoundException e) {
            throw new MemberException(ErrorCode.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new MemberException(ErrorCode.FILE_LOAD_ERROR);
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Member member : store.values()) {
                String line = member.getId() + SEPARATOR +
                        member.getName() + SEPARATOR +
                        member.getBirthday() + SEPARATOR +
                        member.getEmail() + SEPARATOR +
                        member.getGender();
                System.out.println(line);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new MemberException(ErrorCode.FILE_SAVE_ERROR);
        }
    }
}