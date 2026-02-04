package com.gym.gymapp.controller;

import com.gym.gymapp.model.Member;
import com.gym.gymapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FileUploadController implements WebMvcConfigurer {

    private final String UPLOAD_DIR = "C:/Users/VAIBHAV/Desktop/GymManagment/backend/uploads";

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///" + UPLOAD_DIR + "/");
    }

    @PostMapping("/members/{id}/upload")
    public ResponseEntity<?> uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) return ResponseEntity.badRequest().body("No file uploaded");

            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // filename = member_{id}_{timestamp}.ext
            String filename = "member_" + id + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, filename);
            Files.write(filePath, file.getBytes());

            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            member.setPhotoUrl("/uploads/" + filename); // save relative URL
            memberRepository.save(member);

            return ResponseEntity.ok(Map.of("url", member.getPhotoUrl()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }
}
