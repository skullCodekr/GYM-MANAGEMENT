package com.gym.gymapp.model;

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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public WorkoutEntry() {}

    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getExercise() { return exercise; }
    public void setExercise(String exercise) { this.exercise = exercise; }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
}
