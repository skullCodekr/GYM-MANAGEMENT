package com.gym.gymapp.repository;

import com.gym.gymapp.model.WorkoutEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutEntryRepository extends JpaRepository<WorkoutEntry, Long> {}
