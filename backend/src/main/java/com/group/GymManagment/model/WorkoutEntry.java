package com.group.GymManagment.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "workout_entries")
public class WorkoutEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String exercise;
    private int sets;
    private int reps;
    private double weightUsed; // in kg

    @ManyToOne(fetch = FetchType.EAGER) // default is EAGER for ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // linking workout to a member

    public WorkoutEntry() {}

    public WorkoutEntry(LocalDate date, String exercise, int sets, int reps, double weightUsed, Member member) {
        this.date = date;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weightUsed = weightUsed;
        this.member = member;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getExercise() { return exercise; }
    public void setExercise(String exercise) { this.exercise = exercise; }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public double getWeightUsed() { return weightUsed; }
    public void setWeightUsed(double weightUsed) { this.weightUsed = weightUsed; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
}
