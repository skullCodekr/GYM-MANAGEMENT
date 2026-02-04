package com.gym.gymapp.service;

import com.gym.gymapp.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends membership expiry email to a member.
     * Matches exactly with Member.java field names.
     */
    public void sendExpiryMail(Member member) {
        try {
            if (member.getEmail() == null || member.getEmail().isEmpty()) {
                System.out.println("Skipping email, member has no email: " + member.getName());
                return;
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(member.getEmail());
            message.setSubject("Gym Membership Expired");
            message.setText(
                    "Hello " + member.getName() + ",\n\n" +
                            "Your gym membership has expired on " + member.getEndDate() + ".\n" +
                            "Please renew your membership to continue services.\n\n" +
                            "Regards,\nGym Management"
            );

            mailSender.send(message);
            System.out.println("Expiry email sent successfully to: " + member.getEmail());

        } catch (Exception e) {
            System.err.println("Failed to send email to: " + member.getEmail());
            e.printStackTrace();
        }
    }
}
