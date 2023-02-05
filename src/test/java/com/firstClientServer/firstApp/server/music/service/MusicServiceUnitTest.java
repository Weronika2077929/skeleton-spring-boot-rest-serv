package com.firstClientServer.firstApp.server.music.service;

import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import com.firstClientServer.firstApp.server.music.handler.TrackNotFoundException;
import com.firstClientServer.firstApp.server.music.repository.TrackRepository;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MusicServiceUnitTest {

    @InjectMocks
    MusicService musicService;

    @Mock
    TrackRepository trackRepo;

    @Test
    void testSaveTrack_givenNewTrack_returnSuccessTrack() throws ValidationException {
        TrackEntity track = new TrackEntity(1l, "Title", "Artist", "1998");
        Mockito.when(trackRepo.existsById(Mockito.anyLong())).thenReturn(false);
        Mockito.when(trackRepo.save(Mockito.any())).thenReturn(track);
        TrackEntity returnedTrack = musicService.saveTrack(track);
        Assertions.assertEquals(track, returnedTrack);
        Mockito.verify(trackRepo, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verify(trackRepo, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testSaveTrack_givenExistingTrack_returnValidationException() throws ValidationException {
        TrackEntity track = new TrackEntity(1l, "Title", "Artist", "1998");
        Mockito.when(trackRepo.existsById(Mockito.anyLong())).thenReturn(true);

        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> {
            musicService.saveTrack(track);
        });
        Assertions.assertEquals("Track already exists", validationException.getMessage());
        Mockito.verify(trackRepo, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUpdateTrack_givenNewTrack_returnValidationException() throws ValidationException {
        TrackEntity track = new TrackEntity(1l, "Title", "Artist", "1998");
        String expectedError = String.format("Track with id: %s not found", track.getId());

        Mockito.when(trackRepo.existsById(Mockito.anyLong())).thenReturn(false);

        TrackNotFoundException validationException = Assertions.assertThrows(TrackNotFoundException.class, () -> {
            musicService.updateTrack(track);
        });
        Assertions.assertEquals(expectedError, validationException.getMessage());
        Mockito.verify(trackRepo,Mockito.never()).save(Mockito.any());
    }
}