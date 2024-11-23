package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<User> getTraining(Long trainingId);

    /**
     * Retrieves all training records.
     *
     * @return An {@link Optional} containing the all users,
     */
    List<Training> findAllTrainings();

    /**
     * Retrieves all trainings for user based on userId.
     *
     * @param userId id of the user whose trainings should be retrieved
     * @return a list of {@link Training} objects associated with the specified user
     *              or empty list if no such trainings exist
     */
    List<Training> getTrainingsForUser(Long userId);

    /**
     * Retrieves all trainings that were finished after a specific date.
     *
     * @param time the {@link LocalDate} representing the date after which
     *             trainings were finished
     * @return a list of {@link Training} objects, which were finished after specific date
     *              or empty list if no such trainings exist
     */
    List<Training> getFinishedTrainingsAfterTime(LocalDate time);

    /**
     * Retrieves all trainings with specific activityType.
     *
     * @param activityType a {@link String} representing specific type of activity
     * @return a list of {@link Training} objects that match the specified activity type
     *              or empty list if no such trainings exist
     */
    List<Training> getTrainingByActivityType(String activityType);
}
