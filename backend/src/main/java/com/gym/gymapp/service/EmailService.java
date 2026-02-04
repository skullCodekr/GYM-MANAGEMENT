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

    public void sendExpiryMail(Member member) {

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
    }
}
