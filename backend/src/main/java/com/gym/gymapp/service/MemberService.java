package com.gym.gymapp.service;

import com.gym.gymapp.model.Member;
import com.gym.gymapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailService emailService;

    // ========================
    // UPDATE MEMBER WITH EXPIRY LOGIC
    // ========================
    public Member updateMember(Long id, Member updated) {

        // Fetch member safely
        Optional<Member> opt = memberRepository.findById(id);
        if (!opt.isPresent()) {
            throw new RuntimeException("Member not found");
        }
        Member member = opt.get();

        // ---------- BASIC FIELDS ----------
        if (updated.getName() != null) member.setName(updated.getName());
        if (updated.getEmail() != null) member.setEmail(updated.getEmail());
        if (updated.getPhone() != null) member.setPhone(updated.getPhone());

        // 🔥 FIXED PART (PHOTO ISSUE)
        if (updated.getPhotoUrl() != null && !updated.getPhotoUrl().isBlank()) {
            member.setPhotoUrl(updated.getPhotoUrl());
        }

        if (updated.getJoiningDate() != null) member.setJoiningDate(updated.getJoiningDate());
        if (updated.getDuration() != null) member.setDuration(updated.getDuration());
        if (updated.getFee() != null) member.setFee(updated.getFee());

        // ---------- CORE LOGIC ----------
        if (member.getJoiningDate() != null && member.getDuration() != null) {
            int months;
            try {
                months = Integer.parseInt(member.getDuration());
            } catch (NumberFormatException e) {
                throw new RuntimeException("Duration must be a number (months)");
            }

            LocalDate endDate = member.getJoiningDate().plusMonths(months);
            member.setEndDate(endDate);

            LocalDate today = LocalDate.now();

            if (today.isAfter(endDate)) {
                // Membership expired
                member.setFeeStatus("DUE");

                // Send reminder mail only once
                if (!Boolean.TRUE.equals(member.getReminderSent())) {
                    emailService.sendExpiryMail(member);
                    member.setReminderSent(true);
                }

            } else {
                // Membership active
                member.setFeeStatus("PAID");
                member.setReminderSent(false); // reset reminder flag
            }
        }

        // Save updated member
        return memberRepository.save(member);
    }
}
