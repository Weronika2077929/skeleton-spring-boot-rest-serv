package com.firstClientServer.firstApp.client;


import com.firstClientServer.firstApp.client.entity.TrackEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class Calculator {

    private final MusicClient musicClient;
    private final Clock clock;
//    @Scheduled(fixedDelay = 10000)
    public List<TrackEntity> getTrackList() {
        Instant loggedTime = clock.instant();
        log.info("Calling getTrackList endpoint {}", LocalDateTime.ofInstant(loggedTime, clock.getZone()));
        List<TrackEntity> data = musicClient.getTrackList();
        log.info("Track List: {}" , data);
        return data;
    }

    public TrackEntity getTrack(String id) {
        Instant loggedTime = clock.instant();
        log.info("Calling getTrack {} endpoint {}", id, LocalDateTime.ofInstant(loggedTime, clock.getZone()));
        TrackEntity track = musicClient.getTrack(id);
        log.info("Track: {} ", track);
        return track;
    }

    public TrackEntity saveTrack(TrackEntity track) {
        Instant loggedTime = clock.instant();
        log.info("Calling saveTrack {} endpoint {}", track, LocalDateTime.ofInstant(loggedTime, clock.getZone()));
        TrackEntity trackResponse = musicClient.saveTrack(track);
        log.info("Track: {} ", track);
        return trackResponse;
    }
}
