package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Adds a new training.
     *
     * @param trainingDto the {@link TrainingDto} containing information for the training session to create
     * @return the created {@link TrainingDto}
     * @throws InterruptedException if an interruption occurs during processing
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TrainingDto addTraining(@RequestBody TrainingDto trainingDto) throws InterruptedException {
        System.out.println("Training for user with ID: " + trainingDto.user().getId() + " passed to the request");



        return trainingService.createTraining(tempTraining);
    }
}
