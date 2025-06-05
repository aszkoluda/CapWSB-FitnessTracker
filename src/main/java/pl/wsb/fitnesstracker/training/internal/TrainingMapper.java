package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.User;

@Component
public class TrainingMapper {

    public TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(),
                training.getUser() != null ? training.getUser().getId() : null,
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    public Training toEntity(TrainingDto dto, User user) {
        Training training = new Training();
        training.setUser(user);
        training.setStartTime(dto.startTime());
        training.setEndTime(dto.endTime());
        training.setActivityType(dto.activityType());
        training.setDistance(dto.distance());
        training.setAverageSpeed(dto.averageSpeed());
        return training;
    }

    public Training toEntityWithId(TrainingDto dto, User user) {
        Training training = toEntity(dto, user);
        training.setId(dto.id());
        return training;
    }
}