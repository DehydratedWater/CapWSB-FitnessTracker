package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.user.api.User;

public interface TrainingService {
    Training createTraining(TrainingDto trainingDto);
}
