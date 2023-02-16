package com.firstClientServer.firstApp.server.music.handler;

import com.firstClientServer.firstApp.server.music.repository.TrackRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ExistsValidator implements
        ConstraintValidator<Exists_wl, Long> {

    private final TrackRepository trackRepo;
    @Override
    public void initialize(Exists_wl existsValidator) {
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext cxt) {
        if (id == null) {
            return false;
        }
        return trackRepo.existsById(id);
    }

}
