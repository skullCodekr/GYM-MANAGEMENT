package com.gym.gymapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class SupabaseService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseApiKey;

    @Value("${supabase.bucket}")
    private String supabaseBucket;

    private final RestTemplate restTemplate;

    public SupabaseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Uploads file to Supabase Storage and returns PUBLIC URL
     */
    public String uploadPhoto(MultipartFile file) throws IOException {

        // unique filename to avoid overwrite
        String fileName =
                UUID.randomUUID() + "_" + file.getOriginalFilename();

        String uploadUrl =
                supabaseUrl + "/storage/v1/object/" +
                        supabaseBucket + "/" + fileName;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(supabaseApiKey);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpEntity<byte[]> entity =
                new HttpEntity<>(file.getBytes(), headers);

        // POST = new upload (safe)
        restTemplate.exchange(
                uploadUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        // public URL (THIS is what you store in DB)
        return supabaseUrl +
                "/storage/v1/object/public/" +
                supabaseBucket + "/" + fileName;
    }
}
