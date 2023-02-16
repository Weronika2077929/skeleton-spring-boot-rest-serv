package com.firstClientServer.firstApp;

import com.firstClientServer.firstApp.server.music.controller.MusicController;
import com.firstClientServer.firstApp.server.music.controller.payload.TrackCreateRequest;
import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import com.firstClientServer.firstApp.server.music.repository.TrackRepository;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FirstAppApplicationIntegrationTests {

    @Autowired
    private MusicController musicController;

    @Autowired
    private TrackRepository trackRepo;

    @Test
    void contextLoads() {
    }


    @Test
    void testMusicControllerAutowired() {
        assertThat(musicController).isNotNull();
    }

    @Test
    void testCreateTrack() throws ValidationException {

        TrackCreateRequest trackRequest = new TrackCreateRequest("Title", "Artist", "2023");

        musicController.saveTrack(trackRequest);
        Iterable<TrackEntity> tracks = trackRepo.findAll();
        assertThat(tracks).extracting(TrackEntity::getTitle).contains("Title");
    }

}
