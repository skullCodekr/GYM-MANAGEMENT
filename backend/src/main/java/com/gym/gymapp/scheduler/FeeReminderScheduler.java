package com.gym.gymapp.scheduler;

import com.gym.gymapp.model.Member;
import com.gym.gymapp.repository.MemberRepository;
import com.gym.gymapp.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FeeReminderScheduler {

    private final MemberRepository memberRepository;
    private final EmailService mailService;

    public FeeReminderScheduler(MemberRepository memberRepository, EmailService mailService) {
        this.memberRepository = memberRepository;
        this.mailService = mailService;
    }

    // Scheduler runs every day at 9 AM
    // Cron: second, minute, hour, day-of-month, month, day-of-week
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendFeeDueMails() {
        LocalDate today = LocalDate.now(); // system date use ho raha hai

        System.out.println("🔔 Running Fee Reminder Scheduler for: " + today);

        // =============================
        // Fetch all members whose fee is DUE and EndDate <= today
        // =============================
        List<Member> overdueMembers = memberRepository.findByEndDateBeforeAndFeeStatus(today.plusDays(1), "DUE");

        if (overdueMembers.isEmpty()) {
            System.out.println("ℹ️ No members with fee due today.");
            return;
        }

        for (Member member : overdueMembers) {
            if (member.getReminderSent() == null || !member.getReminderSent()) {
                try {
                    mailService.sendExpiryMail(member);
                    member.setReminderSent(true);
                    memberRepository.save(member);
                    System.out.println("📧 Fee due mail sent to: " + member.getEmail());
                } catch (Exception e) {
                    System.err.println("❌ Failed to send mail to: " + member.getEmail());
                    e.printStackTrace();
                }
            } else {
                System.out.println("ℹ️ Reminder already sent to: " + member.getEmail());
            }
        }
    }
}
