package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 *
 * @param id            the unique identifier of the training session (new training sessions do not contain an id, as it is auto-generated)
 * @param userId        the identifier of user who performed the training
 * @param startTime     the start time of the training session
 * @param endTime       the end time of the training session
 * @param activityType  the type of activity performed during the training (e.g., RUNNING, SWIMMING)
 * @param distance      the distance covered during the training session, measured in appropriate units (e.g., kilometers or miles)
 * @param averageSpeed  the average speed during the training session, calculated based on distance and duration
 */
public record TrainingReqBodyDto(
        Long id,
        Long userId,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date startTime,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date endTime,
        ActivityType activityType,
        double distance,
        double averageSpeed
) {
}