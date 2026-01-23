package com.group.GymManagment.service;

import com.group.GymManagment.model.WorkoutEntry;
import com.group.GymManagment.repository.WorkoutEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutEntryService {

    @Autowired
    private WorkoutEntryRepository workoutEntryRepository;

    public List<WorkoutEntry> getAllWorkouts() {
        return workoutEntryRepository.findAll();
    }

    public Optional<WorkoutEntry> getWorkoutById(Long id) {
        return workoutEntryRepository.findById(id);
    }

    public WorkoutEntry createWorkout(WorkoutEntry workoutEntry) {
        return workoutEntryRepository.save(workoutEntry);
    }

    public WorkoutEntry updateWorkout(Long id, WorkoutEntry updatedWorkout) {
        return workoutEntryRepository.findById(id)
                .map(workout -> {
                    workout.setDate(updatedWorkout.getDate());
                    workout.setExercise(updatedWorkout.getExercise());
                    workout.setSets(updatedWorkout.getSets());
                    workout.setReps(updatedWorkout.getReps());
                    workout.setWeightUsed(updatedWorkout.getWeightUsed());
                    return workoutEntryRepository.save(workout);
                })
                .orElseGet(() -> {
                    updatedWorkout.setId(id);
                    return workoutEntryRepository.save(updatedWorkout);
                });
    }

    public void deleteWorkout(Long id) {
        workoutEntryRepository.deleteById(id);
    }
}
