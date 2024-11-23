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

    /**
     * Retrieves all trainings in the system.
     *
     * @return a {@link List} of all {@link Training} entities
     */
    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    /**
     * Retrieves trainings of user specified by userID.
     *
     * @param userId id of the user whose trainings should be retrieved
     * @return a {@link List} of all {@link Training} entities associated with specified user
     *              or empty list if no such trainings exist
     */
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

    /**
     * Retrieves trainings done after specific date
     *
     * @param time the {@link LocalDate} representing the date after which
     *             trainings were finished
     * @return a {@link List} of all {@link Training} trainings done after specific date
     *             or empty list if no such trainings exist
     */
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

    /**
     * Retrieves all trainings with specific activityType.
     *
     * @param activityType a {@link String} representing specific type of activity
     * @return a list of {@link Training} objects that match the specified activity type
     *              or empty list if no such trainings exist
     */
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

    /**
     * Creates a new {@link Training} entity.
     *
     * @param trainingsDto the {@link TrainingReqBodyDto} object to be created
     * @return the newly created {@link Training} object with assigned ID and persisted state
     */
    @Override
    public Training createTraining(TrainingReqBodyDto trainingsDto){
        Long userId = trainingsDto.getUserId();
        Optional<User> optionalUser = userProvider.getUser(userId);
        User tempUser = optionalUser.orElseThrow(() -> new IllegalArgumentException("User is not existing in DB, action is not permitted!"));

        Training tempTraining = new Training(tempUser, trainingsDto.getStartTime(), trainingsDto.getEndTime(), trainingsDto.getActivityType(), trainingsDto.getDistance(), trainingsDto.getAverageSpeed());
        //log.info("Creating Trainings{}", tempTraining);

        return trainingRepository.save(tempTraining);
    }

    /**
     * Updates an existing {@link Training} entity with new values.
     *
     * @param trainingId the identifier of the training to be changed
     * @param trainingsDto the {@link TrainingReqBodyDto} object containing updated data
     * @return the updated {@link Training} object after persistence
     */
    @Override
    public Training updateTraining(Long trainingId, TrainingReqBodyDto trainingsDto){
        Long userId = trainingsDto.getUserId();
        Optional<User> optionalUser = userProvider.getUser(userId);
        User tempUser = optionalUser.orElseThrow(() -> new IllegalArgumentException("User is not existing in DB, action is not permitted!"));

        Optional<Training> optionalTraining = trainingRepository.findById(trainingId);

        if (!optionalTraining.isPresent()){
            throw new IllegalArgumentException("Training is not existing in DB, action is not permitted!");
        }
        Training newTraining = new Training(trainingId, tempUser, trainingsDto.getStartTime(), trainingsDto.getEndTime(), trainingsDto.getActivityType(), trainingsDto.getDistance(), trainingsDto.getAverageSpeed());
        //log.info("Creating Trainings{}", newTraining);

        return trainingRepository.save(newTraining);
    }
}
