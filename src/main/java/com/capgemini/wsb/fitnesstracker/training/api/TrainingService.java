package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.user.api.User;

/**
 * Interface (API) for modifying operations on {@link Training} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface TrainingService {
    /**
     * Creates a new {@link Training} entity.
     *
     * @param trainingDto the {@link TrainingReqBodyDto} object to be created
     * @return the newly created {@link Training} object with assigned ID and persisted state
     */
    Training createTraining(TrainingReqBodyDto trainingDto);

    /**
     * Updates an existing {@link Training} entity with new values.
     *
     * @param trainingId the identifier of the training to be changed
     * @param trainingsDto the {@link TrainingReqBodyDto} object containing updated data
     * @return the updated {@link Training} object after persistence
     */
    Training updateTraining(Long trainingId, TrainingReqBodyDto trainingsDto);
}
