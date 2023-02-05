package com.firstClientServer.firstApp.server.music.service;

import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import com.firstClientServer.firstApp.server.music.handler.TrackNotFoundException;
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
        if( trackRepo.existsById(track.getId()) ) {
            throw new ValidationException("Track already exists");
        }
        return trackRepo.save(track);
    }

    public TrackEntity getTrack(long id) {
        return trackRepo.findById(id).orElseThrow();
    }

    public TrackEntity updateTrack(TrackEntity track) throws ValidationException, TrackNotFoundException {
        if( !isPresent(track.getId()))
            throw new TrackNotFoundException(track);
        return trackRepo.save(track);
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
