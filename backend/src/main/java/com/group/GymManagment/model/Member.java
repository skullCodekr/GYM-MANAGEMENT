package com.group.GymManagment.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;
    private Integer age;
    private Double heightCm;
    private Double weightKg;
    private String membershipType;  // Monthly, Yearly etc.
    private LocalDate joinDate;

    public Member() {}

    public Member(String name, String phone, String email, Integer age,
                  Double heightCm, Double weightKg, String membershipType, LocalDate joinDate) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.membershipType = membershipType;
        this.joinDate = joinDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Double getHeightCm() { return heightCm; }
    public void setHeightCm(Double heightCm) { this.heightCm = heightCm; }

    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }

    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
}