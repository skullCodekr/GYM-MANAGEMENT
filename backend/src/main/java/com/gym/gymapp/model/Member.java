package com.gym.gymapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = true)
    private String photoUrl; // <-- this is the photo field

    @Column(nullable = true)
    private LocalDate joiningDate;

    @Column(nullable = true)
    private String duration;

    @Column(nullable = true)
    private Double fee;

    @Column(nullable = true)
    private String feeStatus;

    @Column(nullable = true)
    private LocalDate endDate;

    @Column(nullable = false)
    private Boolean reminderSent = false;
}
