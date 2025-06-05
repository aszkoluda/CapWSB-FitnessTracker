package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.ActivityType;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingProvider trainingProvider;
    private final TrainingMapper trainingMapper;
    private final UserProvider userProvider;

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingProvider.getAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/user/{userId}")
    public List<TrainingDto> getTrainingsByUser(@PathVariable Long userId) {
        return trainingProvider.getTrainingsByUserId(userId)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/after")
    public List<TrainingDto> getTrainingsAfterDate(@RequestParam String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = sdf.parse(date);
            return trainingProvider.getTrainingsAfterDate(parsedDate)
                    .stream()
                    .map(trainingMapper::toDto)
                    .toList();
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format, expected yyyy-MM-dd");
        }
    }

    @GetMapping("/activity")
    public List<TrainingDto> getTrainingsByActivity(@RequestParam String activity) {
        ActivityType type = ActivityType.valueOf(activity.toUpperCase());
        return trainingProvider.getTrainingsByActivity(type)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @PostMapping
    public ResponseEntity<TrainingDto> addTraining(@RequestBody TrainingDto dto) {
        User user = userProvider.getUser(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Training saved = trainingProvider.createTraining(trainingMapper.toEntity(dto, user));
        return ResponseEntity.ok(trainingMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) {
        User user = userProvider.getUser(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Training updated = trainingProvider.updateTraining(id, trainingMapper.toEntity(dto, user));
        return ResponseEntity.ok(trainingMapper.toDto(updated));
    }
}