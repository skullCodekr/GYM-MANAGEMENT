package com.group.GymManagment.controller;

import com.group.GymManagment.model.WorkoutEntry;
import com.group.GymManagment.model.Member;
import com.group.GymManagment.repository.WorkoutEntryRepository;
import com.group.GymManagment.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workouts")
@CrossOrigin(origins = "http://localhost:3000")
public class WorkoutEntryController {

    @Autowired
    private WorkoutEntryRepository workoutEntryRepository;

    @Autowired
    private MemberRepository memberRepository;

    // Get all workouts
    @GetMapping
    public List<WorkoutEntry> getAllWorkouts() {
        return workoutEntryRepository.findAll();
    }

    // Get workout by ID
    @GetMapping("/{id}")
    public Optional<WorkoutEntry> getWorkoutById(@PathVariable Long id) {
        return workoutEntryRepository.findById(id);
    }

    // Add workout entry
    @PostMapping
    public WorkoutEntry createWorkout(@RequestBody WorkoutEntry workoutEntry) {
        Long memberId = workoutEntry.getMember() != null ? workoutEntry.getMember().getId() : null;

        if (memberId == null) {
            throw new RuntimeException("Member ID is required");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + memberId));

        workoutEntry.setMember(member);

        return workoutEntryRepository.save(workoutEntry);
    }

    // Update workout entry
    @PutMapping("/{id}")
    public WorkoutEntry updateWorkout(@PathVariable Long id, @RequestBody WorkoutEntry updatedWorkout) {
        return workoutEntryRepository.findById(id).map(workout -> {
            workout.setDate(updatedWorkout.getDate());
            workout.setExercise(updatedWorkout.getExercise());
            workout.setSets(updatedWorkout.getSets());
            workout.setReps(updatedWorkout.getReps());
            workout.setWeightUsed(updatedWorkout.getWeightUsed());

            // Update member if provided
            if (updatedWorkout.getMember() != null) {
                Long memberId = updatedWorkout.getMember().getId();
                Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new RuntimeException("Member not found with id " + memberId));
                workout.setMember(member);
            }

            return workoutEntryRepository.save(workout);
        }).orElseThrow(() -> new RuntimeException("Workout not found with id " + id));
    }
}
