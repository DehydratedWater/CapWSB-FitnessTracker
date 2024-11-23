package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

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