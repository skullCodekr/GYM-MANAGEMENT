package com.gym.gymapp.controller;

import com.gym.gymapp.model.Member;
import com.gym.gymapp.repository.MemberRepository;
import com.gym.gymapp.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable Long id) {
        Optional<Member> opt = memberRepo.findById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.status(404).body("Member not found");
    }

    @PostMapping
    public Member addMember(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone
    ) {
        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPhone(phone);
        member.setFeeStatus("DUE");
        member.setFee(0.0);
        member.setReminderSent(false);
        return memberRepo.save(member);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(
            @PathVariable Long id,
            @RequestBody Member updated
    ) {
        try {
            Member saved = memberService.updateMember(id, updated);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        Optional<Member> opt = memberRepo.findById(id);
        if (!opt.isPresent()) {
            return ResponseEntity.status(404).body("Member not found");
        }
        memberRepo.deleteById(id);
        return ResponseEntity.ok("Member deleted");
    }
}