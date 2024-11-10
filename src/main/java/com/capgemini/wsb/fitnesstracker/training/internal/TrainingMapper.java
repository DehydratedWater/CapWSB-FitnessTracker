package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {
    /**
     * Converts a {@link Training} entity to a {@link TrainingDto}.
     *
     * @param training the {@link Training} entity to convert
     * @return a {@link TrainingDto} representing the provided {@link Training} entity
     */
    public TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(),
                training.getUser(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }
}
