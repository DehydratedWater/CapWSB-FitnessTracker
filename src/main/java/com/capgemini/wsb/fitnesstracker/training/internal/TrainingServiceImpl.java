package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
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
public class TrainingServiceImpl implements TrainingProvider {
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



}
