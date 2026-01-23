package com.group.GymManagment.repository;

import com.group.GymManagment.model.WorkoutEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutEntryRepository extends JpaRepository<WorkoutEntry, Long> {
}
