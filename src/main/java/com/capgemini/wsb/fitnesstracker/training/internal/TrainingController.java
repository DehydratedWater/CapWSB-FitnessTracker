package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingReqBodyDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public List<TrainingDto> getAllTrainingsForUser(@PathVariable long id) {
        return trainingService.getTrainingsForUser(id)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getAllFinishedTrainingsAfterTime(@PathVariable String afterTime) {
        LocalDate parsedTime = LocalDate.parse(afterTime);
        return trainingService.getFinishedTrainingsAfterTime(parsedTime)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/activityType")
    public List<TrainingDto> getAllTrainingByActivityType(@RequestParam String activityType) {

        return trainingService.getTrainingByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Training addTraining(@RequestBody TrainingReqBodyDto trainingDto) throws InterruptedException {
        //System.out.println("Training " + trainingDto.toString());
        return trainingService.createTraining(trainingDto);
    }

    @PutMapping("/{trainingId}")
    public Training updateTraining(@PathVariable long trainingId, @RequestBody TrainingReqBodyDto trainingDto) throws InterruptedException {
        return trainingService.updateTraining(trainingId, trainingDto);
    }
}
