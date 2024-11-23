package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingReqBodyDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    /**
     * Retrieves a list of all trainings
     *
     * @return a list of {@link TrainingDto} representing all trainings or empty list if
     *                  no trainings exist in DB
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Retrieves detailed information about trainings done by user with specified ID.
     *
     * @param id the ID of the user whose trainings are going to be retrieved
     * @return a list of {@link TrainingDto} objects associated with the specified user
     *      *              or empty list if no such trainings exist
     */
    @GetMapping("/{id}")
    public List<TrainingDto> getAllTrainingsForUser(@PathVariable long id) {
        return trainingService.getTrainingsForUser(id)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Retrieves all trainings that were finished after a specific date.
     *
     * @param afterTime the {@link String} representing the date after which
     *                      trainings were finished
     * @return a list of {@link TrainingDto} objects, which were finished after specific date
     *              or empty list if no such trainings exist
     */
    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getAllFinishedTrainingsAfterTime(@PathVariable String afterTime) {
        LocalDate parsedTime = LocalDate.parse(afterTime);
        return trainingService.getFinishedTrainingsAfterTime(parsedTime)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Retrieves all trainings with specified activityType.
     *
     * @param activityType a {@link String} representing specific type of activity
     * @return a list of {@link TrainingDto} objects that match the specified activity type
     *              or empty list if no such trainings exist
     */
    @GetMapping("/activityType")
    public List<TrainingDto> getAllTrainingByActivityType(@RequestParam String activityType) {

        return trainingService.getTrainingByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Adds a new training.
     *
     * @param trainingDto the {@link TrainingReqBodyDto} object containing information of the training to create
     * @return the created {@link Training} object
     * @throws InterruptedException if an interruption occurs during processing
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Training addTraining(@RequestBody TrainingReqBodyDto trainingDto) throws InterruptedException {
        //System.out.println("Training " + trainingDto.toString());
        return trainingService.createTraining(trainingDto);
    }

    /**
     * Updates an existing training.
     *
     * @param trainingId the ID of the training to update
     * @param trainingDto the {@link TrainingReqBodyDto} object containing updated training information
     * @return the updated {@link Training} object
     * @throws InterruptedException if an interruption occurs during processing
     */
    @PutMapping("/{trainingId}")
    public Training updateTraining(@PathVariable long trainingId, @RequestBody TrainingReqBodyDto trainingDto) throws InterruptedException {
        return trainingService.updateTraining(trainingId, trainingDto);
    }
}
