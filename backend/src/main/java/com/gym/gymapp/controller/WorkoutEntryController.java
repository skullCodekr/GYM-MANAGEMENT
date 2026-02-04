package com.gym.gymapp.controller;

import com.gym.gymapp.model.Member;
import com.gym.gymapp.model.WorkoutEntry;
import com.gym.gymapp.repository.MemberRepository;
import com.gym.gymapp.repository.WorkoutEntryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutEntryController {

    private final WorkoutEntryRepository workoutRepo;
    private final MemberRepository memberRepo;

    public WorkoutEntryController(WorkoutEntryRepository workoutRepo, MemberRepository memberRepo) {
        this.workoutRepo = workoutRepo;
        this.memberRepo = memberRepo;
    }

    @PostMapping
    public WorkoutEntry add(@RequestBody WorkoutEntry w) {
        Member m = memberRepo.findById(w.getMember().getId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        w.setMember(m);
        return workoutRepo.save(w);
    }

    @GetMapping
    public List<WorkoutEntry> all() {
        return workoutRepo.findAll();
    }
}
