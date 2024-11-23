package com.capgemini.wsb.fitnesstracker.training.api;

public interface TrainingService {
    Training createTraining(TrainingReqBodyDto trainingDto);
    Training updateTraining(Long trainingId, TrainingReqBodyDto trainingsDto);
}
