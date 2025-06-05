package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.ActivityType;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    @Override
    public Optional<Training> getTraining(Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsByUserId(Long userId) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getUser() != null && training.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Training> getTrainingsAfterDate(Date date) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getEndTime() != null && training.getEndTime().after(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Training> getTrainingsByActivity(ActivityType activityType) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getActivityType() != null && training.getActivityType().equals(activityType))
                .collect(Collectors.toList());
    }

    @Override
    public Training createTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public Training updateTraining(Long trainingId, Training updatedTraining) {
        return trainingRepository.findById(trainingId)
                .map(existing -> {
                    // Nie masz setterów, więc trzeba dodać je do klasy Training!
                    existing.setStartTime(updatedTraining.getStartTime());
                    existing.setEndTime(updatedTraining.getEndTime());
                    existing.setActivityType(updatedTraining.getActivityType());
                    existing.setDistance(updatedTraining.getDistance());
                    existing.setAverageSpeed(updatedTraining.getAverageSpeed());
                    return trainingRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Training not found with id: " + trainingId));
    }
}