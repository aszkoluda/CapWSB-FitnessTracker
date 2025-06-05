package pl.wsb.fitnesstracker.training.api;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    Optional<Training> getTraining(Long trainingId);

    List<Training> getAllTrainings();

    List<Training> getTrainingsByUserId(Long userId);

    List<Training> getTrainingsAfterDate(Date date);

    List<Training> getTrainingsByActivity(ActivityType activityType);

    Training createTraining(Training training);

    Training updateTraining(Long trainingId, Training updatedTraining);
}