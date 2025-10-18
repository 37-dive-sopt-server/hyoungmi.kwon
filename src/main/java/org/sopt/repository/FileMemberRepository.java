package org.sopt.repository;

import org.sopt.common.exception.ErrorCode;
import org.sopt.common.exception.FileException;
import org.sopt.common.exception.MemberException;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class FileMemberRepository implements MemberRepository {

    private static final String FILE_PATH = "data/members.txt";
    /*
    * ConcurrentHashMap, AtomicLong, AtomicBoolean: 멀티스레드 환경에서 안전하게 공유 데이터를 다루기 위한 클래스
    * <ConcurrentHashMap>
    * HashMap은 멀티스레드 환경에서 동시에 put/remove하면 구조가 깨질 수 있믕
    * ConcurrentHashMap은 내부적으로 segment 단위로 락을 걸기 때문에 여러 스레드가 접근해도 안전하게 동작하며,
    * 전체 Map에 락을 거는게 아니기 때문에 synchronized보다 훨씬 빠르다.
    * 여기서는 flush 스레드가 store.values()을 읽는 동안 메인 스레드가 put/remove를 실행할 수 있으므로 동시 접근이 안전하게 보장된다.
    *
    * <AtomicLong>
    * 여러 스레드가 동시에 save()을 호출해도 sequence 값이 꼬이지 않고 순차적으로 증가하도록 보장
    *
    * <AtomicBoolean>
    * 내부적으로 CAS(compare-and-set) 연산을 사용해 값을 읽고 바꾸는 것을 원자적으로 처리하므로, flush 중에도 dirty flag을 안전하게 관리 가능
    * (flush 스레드와 main 스레드가 동시에 isDirty를 수정하면 상태가 꼬일 수 있다.)
    * */
    private final Map<Long, Member> store = new ConcurrentHashMap<>();
    private static final String SEPARATOR = ",";
    private final AtomicLong sequence = new AtomicLong(0);
    private final AtomicBoolean isDirty = new AtomicBoolean(false);

    /* 비동기 flush를 위한 executor
    ScheduledExecutorService: 일정 간격으로 flush를 자동 실행하는데 사용
    Executors.newSingleThreadScheduledExecutor(): 단일 스레드로 구성된 ScheduledExecutor 생성 -> flush 작업은 한 번에 하나씩 순차적으로 실행되면 충분하므로 단일 스레드 이용
    */
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    public FileMemberRepository() {
        loadFromFile();

        // 5초마다 자동 flush
        scheduler.scheduleAtFixedRate(() -> {
            try {
                flushToFile();
            } catch (Exception e) {
                // scheduleAtFixedRate()는 예외가 던져지면 스케줄러 자체가 멈춰지므로 예외 던지지 말고 로그만 찍어준다.
                // throw new FileException(ErrorCode.FILE_FLUSH_ERROR);
                System.err.println(e.getMessage());
            }
        }, 5, 5, TimeUnit.SECONDS);  // 첫 실행까지 대기 시간, 반복 실행 간격

        // JVM 종료 시 flush를 통해 데이터 반영해준다.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                scheduler.shutdown();
                scheduler.awaitTermination(1, TimeUnit.SECONDS);  // 대기
                flushToFile();
            } catch (Exception e) {
                throw new FileException(ErrorCode.FILE_FLUSH_ERROR);
            }
        }));
    }

    @Override
    public Member save(Member member) {
        long id = sequence.incrementAndGet();
        Member newMember = new Member(id, member.getName(),
                member.getBirthday(), member.getEmail(),
                member.getGender());
        store.put(newMember.getId(), newMember);
        isDirty.set(true);
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
        Member removedMember = store.remove(id);
        if (removedMember == null) {
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }
        isDirty.set(true);
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

                // 파일에서 불러온 id가 sequence보다 크면 sequence 업데이트 (파일에서 읽은 데이터 기준으로 sequence를 최신 id로 맞춤)
                sequence.updateAndGet(current -> Math.max(current, id));
            }
        } catch (FileNotFoundException e) {
            throw new FileException(ErrorCode.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new FileException(ErrorCode.FILE_LOAD_ERROR);
        }
    }

    private void flushToFile() {
        // 저장되지 않은 변경된 내용이 없으면 스킵
        if (!isDirty.get()) return;

        // store snapshot 생성 → flush 중 save/delete가 block되지 않음
        List<Member> snapshot = new ArrayList<>(store.values());

        File file = new File(FILE_PATH);
        File parentDir = file.getParentFile();

        // 디렉토리가 존재하지 않으면 생성
        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                throw new FileException(ErrorCode.DIRECTORY_CREATE_ERROR);
            }
        }

        File tempFile = new File(FILE_PATH + ".tmp");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            for (Member member : snapshot) {
                String line = member.getId() + SEPARATOR +
                        member.getName() + SEPARATOR +
                        member.getBirthday() + SEPARATOR +
                        member.getEmail() + SEPARATOR +
                        member.getGender();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new FileException(ErrorCode.FILE_SAVE_ERROR);
        }

        // 임시파일 저장 성공 시 임시파일을 실제파일로 변경
        if (!tempFile.renameTo(file)) {
            throw new FileException(ErrorCode.FILE_SAVE_ERROR);
        } else {
            isDirty.set(false);
            System.out.println("✅ 파일 flush 완료");
        }
    }
}