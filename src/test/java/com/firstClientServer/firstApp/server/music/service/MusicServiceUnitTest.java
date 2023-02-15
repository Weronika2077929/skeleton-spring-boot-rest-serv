package com.firstClientServer.firstApp.server.music.service;

import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import com.firstClientServer.firstApp.server.music.handler.TrackNotFoundException;
import com.firstClientServer.firstApp.server.music.repository.TrackRepository;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MusicServiceUnitTest {

    @InjectMocks
    private MusicService musicService;

    @Mock
    private TrackRepository trackRepo;

    @Test
    void testSaveTrack_givenNewTrack_returnSuccessTrack() throws ValidationException {
        TrackEntity track = new TrackEntity(1l, "Title", "Artist", "1998");
        when(trackRepo.existsById(Mockito.anyLong())).thenReturn(false);
        when(trackRepo.save(Mockito.any())).thenReturn(track);
        TrackEntity returnedTrack = musicService.saveTrack(track);
        assertEquals(track, returnedTrack);
        verify(trackRepo, Mockito.times(1)).existsById(Mockito.anyLong());
        verify(trackRepo, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testSaveTrack_givenExistingTrack_returnValidationException() throws ValidationException {
        TrackEntity track = new TrackEntity(1l, "Title", "Artist", "1998");
        when(trackRepo.existsById(Mockito.anyLong())).thenReturn(true);

        ValidationException validationException = assertThrows(ValidationException.class, () -> {
            musicService.saveTrack(track);
        });
        assertEquals("Track already exists", validationException.getMessage());
        verify(trackRepo, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUpdateTrack_givenNewTrack_returnValidationException() throws ValidationException {
        TrackEntity track = new TrackEntity(1l, "Title", "Artist", "1998");
        String expectedError = String.format("Track with id: %s not found", track.getId());

        when(trackRepo.existsById(Mockito.anyLong())).thenReturn(false);

        TrackNotFoundException validationException = assertThrows(TrackNotFoundException.class, () -> {
            musicService.updateTrack(track);
        });
        assertEquals(expectedError, validationException.getMessage());
        verify(trackRepo,Mockito.never()).save(Mockito.any());
    }
}