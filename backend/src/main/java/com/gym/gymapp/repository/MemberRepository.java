package com.gym.gymapp.repository;

import com.gym.gymapp.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    List<Member> findAllByEmail(String email);

    // Scheduler ke liye
    List<Member> findByEndDateAndFeeStatus(LocalDate endDate, String feeStatus);

    // Optional: 3 din pehle reminder
    List<Member> findByEndDateAndFeeStatusAndReminderSent(LocalDate endDate, String feeStatus, boolean reminderSent);
    List<Member> findByEndDateBeforeAndFeeStatus(LocalDate date, String feeStatus);

}
