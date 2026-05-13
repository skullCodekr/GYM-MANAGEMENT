package com.gym.gymapp;

import com.gym.gymapp.model.Member;
import com.gym.gymapp.repository.MemberRepository;
import com.gym.gymapp.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TestMemberCreator {

    private final MemberRepository memberRepository;
    private final EmailService mailService;

    public TestMemberCreator(MemberRepository memberRepository, EmailService mailService) {
        this.memberRepository = memberRepository;
        this.mailService = mailService;
    }

    public void sendTestMemberEmail() {
        String testEmail = "yadavvaibhav965@gmail.com";

        // Avoid duplicates
        List<Member> members = memberRepository.findAllByEmail(testEmail);

        Member member;
        if (members.isEmpty()) {
            // Member create kare agar exist na kare
            member = new Member();
            member.setName("Test User1");
            member.setEmail(testEmail);
            member.setPhone("9949999999");
            member.setJoiningDate(LocalDate.now().minusMonths(1));
            member.setDuration("1 Month");
            member.setFee(500.0);
            member.setFeeStatus("PAID");
            member.setEndDate(LocalDate.now()); // due today
            member.setReminderSent(false);

            memberRepository.save(member);
            System.out.println("✅ Test member created for mail scheduler!");
        } else {
            member = members.get(0);
            System.out.println("ℹ️ Test member already exists. Using the first one.");
        }

        // Email bhej do
        mailService.sendExpiryMail(member);
        System.out.println("call kr");
    }
}
