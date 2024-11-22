package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public List<TrainingDto> GetAllFinishedTrainingsAfterTime(@PathVariable String afterTime) {

        LocalDate parsedTime = LocalDate.parse(afterTime);
        return trainingService.getFinishedTrainingsAfterTime(parsedTime)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }
}
