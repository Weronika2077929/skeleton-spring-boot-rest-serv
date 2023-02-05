package com.firstClientServer.firstApp;

import com.firstClientServer.firstApp.server.music.controller.MusicController;
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
    MusicController musicController;

    @Autowired
    TrackRepository trackRepo;

    @Test
    void contextLoads() {
    }


    @Test
    public void testMusicControllerAutowired() throws Exception {
        assertThat(musicController).isNotNull();
    }

    @Test
    void testCreateTrack() throws ValidationException {
        TrackEntity track = new TrackEntity(5l, "Title", "Artist", "2023");

        musicController.saveTrack(track);
        Iterable<TrackEntity> tracks = trackRepo.findAll();
        assertThat(tracks).extracting(TrackEntity::getTitle).contains("Title");
    }

}
