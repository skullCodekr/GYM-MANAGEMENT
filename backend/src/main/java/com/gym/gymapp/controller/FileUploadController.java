package com.gym.gymapp.controller;
import com.gym.gymapp.service.SupabaseService;
import com.gym.gymapp.model.Member;
import com.gym.gymapp.repository.MemberRepository;
import com.gym.gymapp.service.SupabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SupabaseService supabaseService;

    @PostMapping("/members/{id}/upload")
    public ResponseEntity<?> uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) return ResponseEntity.badRequest().body("No file uploaded");

            // Supabase pe upload karo
            String publicUrl = supabaseService.uploadPhoto(file);

            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            member.setPhotoUrl(publicUrl);
            memberRepository.save(member);

            HashMap<String, String> response = new HashMap<>();
            response.put("url", publicUrl);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }
}