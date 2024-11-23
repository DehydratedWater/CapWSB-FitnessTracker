package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.*;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {
    private final TrainingRepository trainingRepository;
    private UserProvider userProvider;

    @Autowired
    public void TrainingServiceImpl(UserProvider userProvider){
        this.userProvider = userProvider;
    }

    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsForUser(Long userId) {
        List<Training> trainings = trainingRepository.findAll();

        if (userId > trainings.size()) {
            log.info("User {} has no trainings saved in DB.", userId);
        }
        else{
            log.info("Getting trainings of user {}", userId);
        }

        Iterator<Training> iterator = trainings.iterator();
        while (iterator.hasNext()){
            Training tempTraining = iterator.next();
            if(tempTraining.getUser().getId() != userId){
                iterator.remove();
                //log.info("I removed {}", tempTraining.getUser().getId());
            }
        }

        return trainings;
    }

    @Override
    public List<Training> getFinishedTrainingsAfterTime(LocalDate time) {

        List<Training> trainings = trainingRepository.findAll();
        Date timeTempDateType = Date.from(time.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Iterator<Training> iterator = trainings.iterator();
        while (iterator.hasNext()){
            Training tempTraining = iterator.next();
            if(tempTraining.getEndTime().compareTo(timeTempDateType) <= 0){
                iterator.remove();
                //log.info("I removed {}", tempTraining.getUser().getId());
            }
        }
        return trainings;
    }

    @Override
    public List<Training> getTrainingByActivityType(String activityType){
        activityType = activityType.toLowerCase();

        //log.info("Pobralem {}", activityType);
        List<Training> trainings = trainingRepository.findAll();

        Iterator<Training> iterator = trainings.iterator();
        while (iterator.hasNext()){
            Training tempTraining = iterator.next();
            //log.info("{}",tempTraining.getActivityType().toString().toLowerCase());
            if(!tempTraining.getActivityType().toString().toLowerCase().equals(activityType)){
                iterator.remove();
                //log.info("I removed {}", tempTraining.getUser().getId());
            }
        }
        return trainings;
    }

    @Override
    public Training createTraining(TrainingReqBodyDto trainingsDto){
        Long userId = trainingsDto.userId();
        Optional<User> optionalUser = userProvider.getUser(userId);
        User tempUser = optionalUser.orElseThrow(() -> new IllegalArgumentException("User is not existing in DB, action is not permitted!"));

        Training tempTraining = new Training(tempUser, trainingsDto.startTime(), trainingsDto.endTime(), trainingsDto.activityType(), trainingsDto.distance(), trainingsDto.averageSpeed());
        //log.info("Creating Trainings{}", tempTraining);

        return trainingRepository.save(tempTraining);
    }

    @Override
    public Training updateTraining(Long trainingId, TrainingReqBodyDto trainingsDto){
        Long userId = trainingsDto.userId();
        Optional<User> optionalUser = userProvider.getUser(userId);
        User tempUser = optionalUser.orElseThrow(() -> new IllegalArgumentException("User is not existing in DB, action is not permitted!"));

        Optional<Training> optionalTraining = trainingRepository.findById(trainingId);

        if (!optionalTraining.isPresent()){
            throw new IllegalArgumentException("Training is not existing in DB, action is not permitted!");
        }
        Training newTraining = new Training(trainingId, tempUser, trainingsDto.startTime(), trainingsDto.endTime(), trainingsDto.activityType(), trainingsDto.distance(), trainingsDto.averageSpeed());
        //log.info("Creating Trainings{}", newTraining);

        return trainingRepository.save(newTraining);
    }
}
