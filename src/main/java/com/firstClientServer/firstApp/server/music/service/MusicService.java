package com.firstClientServer.firstApp.server.music.service;

import com.firstClientServer.firstApp.server.music.controller.payload.TrackUpdateRequest;
import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import com.firstClientServer.firstApp.server.music.repository.TrackRepository;
import jakarta.xml.bind.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MusicService {

    private final TrackRepository trackRepo;

    public TrackEntity saveTrack(TrackEntity track) throws ValidationException {
        if (trackRepo.existsById(track.getId())) {
            throw new ValidationException("Track already exists");
        }
        return trackRepo.save(track);
    }

    public TrackEntity getTrack(long id) {
        return trackRepo.findById(id).orElseThrow();
    }

    public TrackEntity updateTrack(Long trackId, TrackUpdateRequest track) {

        TrackEntity saveTrack = TrackEntity.builder()
                .id(trackId)
                .title(track.getTitle())
                .artist(track.getArtist())
                .releaseYear(track.getReleaseYear())
                .build();

        return trackRepo.save(saveTrack);
    }

    public List<TrackEntity> getTrackList() {
        return (List<TrackEntity>) trackRepo.findAll();
    }

    public Boolean isPresent(long id) {
        return trackRepo.existsById(id);
    }

    public void deleteTrackById(long id) {
        trackRepo.deleteById(id);
    }


}
