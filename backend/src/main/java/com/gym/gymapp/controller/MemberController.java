package com.gym.gymapp.controller;

import com.gym.gymapp.model.Member;
import com.gym.gymapp.repository.MemberRepository;
import com.gym.gymapp.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private MemberService memberService;

    // ========================
    // GET ALL MEMBERS
    // ========================
    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepo.findAll();
    }

    // ========================
    // GET MEMBER BY ID
    // ========================
    @GetMapping("/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable Long id) {
        Optional<Member> opt = memberRepo.findById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.status(404).body("Member not found");
    }

    // ========================
    // ADD MEMBER
    // ========================
    @PostMapping
    public Member addMember(@RequestBody Member member) {

        if (member.getFeeStatus() == null) {
            member.setFeeStatus("DUE");
        }

        if (member.getFee() == null) {
            member.setFee(0.0);
        }

        member.setReminderSent(false);

        return memberRepo.save(member);
    }

    // ========================
    // UPDATE MEMBER
    // ========================
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

    // ========================
    // DELETE MEMBER
    // ========================
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

