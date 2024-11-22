package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {
    private final TrainingRepository trainingRepository;

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
        //log.info("Wszedlem do getFinished");

        List<Training> trainings = trainingRepository.findAll();
        //List<Training> emptyTrainingList = new ArrayList<>();
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
    public Training createTraining(TrainingDto trainingsDto){

        User tempUser = new User(trainingsDto.user().getId(), trainingsDto.user().getFirstName(), trainingsDto.user().getLastName(), trainingsDto.user().getBirthdate(), trainingsDto.user().getEmail());
        Training tempTraining = new Training(tempUser, trainingsDto.startTime(), trainingsDto.endTime(), trainingsDto.activityType(), trainingsDto.distance(), trainingsDto.averageSpeed());
        log.info("Creating Trainings{}", tempTraining);
        List<Training> tempTrainings = trainingRepository.findAll();
        LinkedHashSet<Integer> trainingsIds = new LinkedHashSet<>();
        LinkedHashSet<Integer> trainingsUsersIds = new LinkedHashSet<>();

        for (Training t : tempTrainings) {
            //System.out.println("Element: " + t);
            trainingsIds.add(t.getId().intValue());
            trainingsUsersIds.add(t.getUser().getId().intValue());
        }

        if (trainingsIds.contains(trainingsDto.id().intValue())){
            //log.info("Training is already in DB {}", trainingsDto.id());
            throw new IllegalArgumentException("Training has already DB ID, action is not permitted!");
        }
        if (!trainingsUsersIds.contains(trainingsDto.user().getId().intValue())){
            //log.info("User {} is not existing in DB", trainingsDto.user().Id());
            throw new IllegalArgumentException("User is not existing in DB, action is not permitted!");
        }

        return trainingRepository.save(tempTraining);
    }
}
